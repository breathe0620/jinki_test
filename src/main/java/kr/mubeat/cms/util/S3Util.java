package kr.mubeat.cms.util;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;

/**
 * Created by moonkyu.lee on 2017. 9. 19..
 */
@Service
public class S3Util {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private AmazonS3Client amazonS3Client;
    private TransferManager transferManager;

    @Value("${spring.profiles}")
    private String environment;

    @Value("${spring.aws.s3-list-size}")
    private Integer s3ListSize;

    @Value("${spring.aws.s3bucket}")
    private String bucket;

    @Value("${spring.aws.s3-output-bucket}")
    private String outputBucket;

    @Autowired
    public S3Util(
        AmazonS3Client amazonS3Client,
        TransferManager transferManager
    ) {
        this.amazonS3Client = amazonS3Client;
        this.transferManager = transferManager;
    }

    public ListObjectsV2Result getList(String bucket, String key) {
        ListObjectsV2Request request = new ListObjectsV2Request();
        request.setBucketName(bucket);
        request.setPrefix(key);
        request.setMaxKeys(s3ListSize);
        return amazonS3Client.listObjectsV2(request);
    }

    public String getFile(String bucket, String key) {
        S3ObjectInputStream s3ObjectInputStream = amazonS3Client.getObject(bucket, key).getObjectContent();
        try {
            return IOUtils.toString(s3ObjectInputStream);
        } catch (IOException e) {}
        return null;
    }

    public void deleteFile(String bucket, String key) {
        amazonS3Client.deleteObject(
            bucket, key
        );
    }

    public CopyObjectResult copyToS3FromS3(String fromBucket, String toBucket, String sourceKey, String destinationKey ) {
        return amazonS3Client.copyObject(
            fromBucket,
            sourceKey,
            toBucket,
            destinationKey
        );
    }

    public String multipartUploadByInputStream(MultipartFile file, String extraKey, String fileContentType) {

        PutObjectRequest putObjectRequest = null;
        Upload upload = null;

        String[] fileFrags = fileContentType.split("\\/");
        String extension = fileFrags[fileFrags.length-1];

        String key = extraKey + "." + extension;

        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(fileContentType);

            putObjectRequest = new PutObjectRequest(bucket, key, file.getInputStream(), objectMetadata);

            if (environment.equals("local")) {
                putObjectRequest.setStorageClass(StorageClass.Standard);
                putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
            }

            upload = transferManager.upload(putObjectRequest);
            upload.waitForCompletion();

            // ToDo putObjectResult 를 이용해서 어떤 검증을 할지 고민해 봐야 함
            file.getInputStream().close();
        }
        catch(InterruptedException | IOException e) {
            e.printStackTrace();
            return null;
        }

        return key;
    }

    public String multipartUploadByFile(MultipartFile file, String extraKey, String fileContentType) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("mm.ss.sss");
        long start = System.currentTimeMillis();

        PutObjectRequest putObjectRequest = null;
        Upload upload = null;

        String[] fileFrags = fileContentType.split("\\/");
        String extension = fileFrags[fileFrags.length-1];

        String key = extraKey + "." + extension;

        try {
            logger.info("path : " + System.getProperty("java.io.tmpdir")+"/");
            File convertFile = new File(System.getProperty("java.io.tmpdir")+"/"+file.getOriginalFilename());
            file.transferTo(convertFile);

            logger.info("file copy done : " + dateFormat.format(System.currentTimeMillis() - start));

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(fileContentType);

            putObjectRequest = new PutObjectRequest(outputBucket, key, convertFile);

            if (environment.equals("local") || environment.equals("dev")) {
                putObjectRequest.setStorageClass(StorageClass.Standard);
                putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
            }

            upload = transferManager.upload(putObjectRequest);
            upload.waitForCompletion();

            // ToDo putObjectResult 를 이용해서 어떤 검증을 할지 고민해 봐야 함
            convertFile.delete();

            logger.info("s3 upload done : " + dateFormat.format(System.currentTimeMillis() - start));
        }
        catch(InterruptedException | IOException e) {
            e.printStackTrace();
            return null;
        }

        return key;
    }

}
