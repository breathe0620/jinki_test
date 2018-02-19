package kr.mubeat.cms.util;

import com.amazonaws.services.cloudfront.AmazonCloudFrontClient;
import com.amazonaws.services.cloudfront.model.*;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * Created by doohwan.yoo on 2017. 6. 8..
 */
@Service
public class AWSUtils {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private AmazonS3Client amazonS3Client;
    private AmazonCloudFrontClient amazonCloudFrontClient;

    @Value("${spring.profiles}")
    private String environment;

    @Value("${spring.aws.s3bucket}")
    private String bucket;

    @Value("${spring.aws.distribution-id}")
    private String distributionId;

    @Value("${spring.aws.default-input-key}")
    private String defaultInputKey;

    @Autowired
    public AWSUtils(
            AmazonS3Client amazonS3Client,
            AmazonCloudFrontClient amazonCloudFrontClient
    ) {
        this.amazonS3Client = amazonS3Client;
        this.amazonCloudFrontClient = amazonCloudFrontClient;
    }

    public String uploadObjectToS3(MultipartFile multipartFile, String extraKey, String fileContentType) {

        PutObjectRequest putObjectRequest = null;
        PutObjectResult putObjectResult = null;

        String[] fileFrags = fileContentType.split("\\/");
        String extension = fileFrags[fileFrags.length-1];

        String key = extraKey + "." + extension;

        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(fileContentType);

            putObjectRequest = new PutObjectRequest(bucket, key, multipartFile.getInputStream(), objectMetadata);

            // 수정한 부분
            if (environment.equals("local") || environment.equals("dev")) {
                putObjectRequest.setStorageClass(StorageClass.Standard);
                putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
            }

            putObjectResult = amazonS3Client.putObject(putObjectRequest);

            // ToDo putObjectResult 를 이용해서 어떤 검증을 할지 고민해 봐야 함
            multipartFile.getInputStream().close();
        }
        catch(IOException e) {
            e.printStackTrace();;
            return null;
        }

        return key;
    }

    public void deleteObjectFromS3(String extraKey) {
        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucket, extraKey);
        amazonS3Client.deleteObject(deleteObjectRequest);
    }

    /**
     * 클라우드 프론트 캐시 오브젝트 무효화
     * 참고자료
     * https://github.com/corley/aws-ant-task/blob/master/src/main/it/corley/ant/CloudFront.java
     * https://stackoverflow.com/questions/28527188/how-to-invalidate-a-fileto-be-refreshed-served-from-cloudfront-cdn-via-java-aw
     * @param extraKey
     */
    public CreateInvalidationResult invalidateCachedObjectFromCloudfront(String extraKey) {
        logger.info("환경 : " + environment);
        if (environment.equals("local"))
            return null;

        Paths pathsList = new Paths().withItems(File.separator + extraKey).withQuantity(1);

        InvalidationBatch invalidationBatch =
                new InvalidationBatch(
                        pathsList,
                        distributionId + String.valueOf((int) System.currentTimeMillis() / 1000)
                );
        CreateInvalidationRequest createInvalidationRequest =
                new CreateInvalidationRequest(
                        distributionId,
                        invalidationBatch
                );

        CreateInvalidationResult createInvalidationResult =
                amazonCloudFrontClient.createInvalidation(
                        createInvalidationRequest
                );

        return createInvalidationResult;
    }

}
