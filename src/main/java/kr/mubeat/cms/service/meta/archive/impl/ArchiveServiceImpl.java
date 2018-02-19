package kr.mubeat.cms.service.meta.archive.impl;

import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import kr.mubeat.cms.annotation.redis.RedisExpire;
import kr.mubeat.cms.annotation.redis.RedisHashExpire;
import kr.mubeat.cms.config.ClipState;
import kr.mubeat.cms.domain.meta.clip.ClipAcquisition;
import kr.mubeat.cms.domain.meta.clip.ClipMeta;
import kr.mubeat.cms.domain.meta.clip.ClipMetaLang;
import kr.mubeat.cms.dto.Result;
import kr.mubeat.cms.dto.meta.acquisition.ProgramSearchDTO;
import kr.mubeat.cms.dto.meta.archive.*;
import kr.mubeat.cms.enumerations.EnumResCode;
import kr.mubeat.cms.enumerations.LangType;
import kr.mubeat.cms.repository.meta.acquisition.AcquisitionRepository;
import kr.mubeat.cms.repository.meta.clip.ClipMetaLangRepository;
import kr.mubeat.cms.repository.meta.clip.ClipMetaRepository;
import kr.mubeat.cms.repository.meta.program.ProgramMetaRepository;
import kr.mubeat.cms.service.meta.archive.ArchiveService;
import kr.mubeat.cms.util.AWSUtils;
import kr.mubeat.cms.util.S3Util;
import kr.mubeat.cms.util.TimeStampUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by moonkyu.lee on 2017. 9. 19..
 */
@Service
public class ArchiveServiceImpl implements ArchiveService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${spring.aws.archive-bucket}")
    private String archiveBucket;

    @Value("${spring.aws.s3-output-bucket}")
    private String outputBucket;

    @Value("${spring.aws.cloudfront-url}")
    private String cloudFrontUrl;

    private AcquisitionRepository acquisitionRepository;
    private ProgramMetaRepository programMetaRepository;
    private ClipMetaRepository clipMetaRepository;
    private ClipMetaLangRepository clipMetaLangRepository;

    private S3Util s3Util;
    private AWSUtils awsUtils;
    private RedisTemplate redisTemplate;
    private TimeStampUtil timeStampUtil;

    @Autowired
    public ArchiveServiceImpl(
        AcquisitionRepository acquisitionRepository,
        ProgramMetaRepository programMetaRepository,
        ClipMetaRepository clipMetaRepository,
        ClipMetaLangRepository clipMetaLangRepository,
        S3Util s3Util,
        AWSUtils awsUtils,
        RedisTemplate redisTemplate,
        TimeStampUtil timeStampUtil
    ) {
        this.acquisitionRepository = acquisitionRepository;
        this.programMetaRepository = programMetaRepository;
        this.clipMetaRepository = clipMetaRepository;
        this.clipMetaLangRepository = clipMetaLangRepository;
        this.s3Util = s3Util;
        this.awsUtils = awsUtils;
        this.redisTemplate = redisTemplate;
        this.timeStampUtil = timeStampUtil;
    }

    /**
     mbc/20141227_쇼! 음악중심, 437회_EP201612191153_20903.xml
     mbc/20141227_쇼! 음악중심, 437회_EP201612191153_20904.xml
     mbc/20141227_쇼! 음악중심, 437회_H264 10M 1920x1080 MP4__20903.mp4
     mbc/20141227_쇼! 음악중심, 437회_H264 10M 1920x1080 MP4__20904.mp4
     */
    @Override
    public List<ArchiveS3DTO> getList(String path) {
        ListObjectsV2Result result = s3Util.getList(archiveBucket, path);
        List<S3ObjectSummary> receivedData = result.getObjectSummaries();
        List<XML> xmlList = new ArrayList<>();
        List<Video> mp4List = new ArrayList<>();
        List<ArchiveS3DTO> data = new ArrayList<>();

        /**
         * reduce .complete
         * name compare - pattern
         */
        for (S3ObjectSummary object : receivedData) {
            if (!object.getKey().contains(".complete")) {
                if (object.getKey().contains(".xml")) {
                    xmlList.add(new XML(object.getKey()));
                } else if (object.getKey().contains(".mp4")) {
                    mp4List.add(new Video(object.getKey()));
                }
            }
        }

        for (XML xml : xmlList) {
            for (Video video : mp4List) {
                if (xml.getCorporator().equals(video.getCorporator())
                    &&
                    xml.getCode().equals(video.getCode())
                    &&
                    xml.getDate().equals(video.getDate())
                ) {
                    data.add(
                        new ArchiveS3DTO(
                            xml.getCorporator(),
                            xml.getDate(),
                            xml.getCode(),
                            xml.getName(),
                            xml.getFullName(),
                            video.getFullName()
                        )
                    );
                }
            }
        }

        return data;
    }

    @Override
    public ArchiveMBC parseXML(String xml, String video) {
        /**
         * 유니코드 BOM 제거를 해야 함 ﻿﻿\uFEFF
         */
        String xmlData = s3Util.getFile(archiveBucket, xml).replaceAll("\r", "").replaceAll("\uFEFF","");
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            InputSource inputSource = new InputSource(new StringReader(xmlData));
            inputSource.setEncoding("UTF-8");
            Document document = documentBuilder.parse(inputSource);
            document.getDocumentElement().normalize();

            NodeList nodeList = document.getElementsByTagName("EPISODE_META");
            Node node = nodeList.item(0);
            Element element = (Element) node;

            String playtime = element.getElementsByTagName("전송영상길이").item(0).getTextContent().replaceAll(":","");
            playtime = playtime.substring(0, playtime.length() -2);
            String second = playtime.substring(playtime.length() -2, playtime.length());
            playtime = playtime.substring(0, playtime.length() -2);
            String minute = playtime.substring(playtime.length() -2, playtime.length());
            playtime = playtime.substring(0, playtime.length() -2);
            String hour = playtime.substring(playtime.length() -2, playtime.length());

            Integer time = Integer.parseInt(second) + Integer.parseInt(minute) * 60 + Integer.parseInt(hour) * 3600;

            ArchiveMBC archiveMBC = new ArchiveMBC(
                xmlData,
                element.getElementsByTagName("제목").item(0).getTextContent(),
                time,
                element.getElementsByTagName("방송일").item(0).getTextContent().replaceAll("-", "")
            );

            archiveMBC.setOriginXML(xml);
            archiveMBC.setOriginVideo(video);

            return archiveMBC;

        } catch (ParserConfigurationException |
            SAXException |
            IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    @Transactional(readOnly = false)
    public Result clipRegist(ArchiveMeta archiveMeta, MultipartFile video, MultipartFile thumbImg) {

        /**
         * 1. tbl_clip_acquisition 에 write 후 clipMetaId 를 가져온다
         * 2. tbl_clip_meta 에 write
         * 3. tbl_clip_meta_lang 에 write
         * 4. mubeats3ftp 에서 vlendingcdn 으로 영상 copy
         * 5. tbl_clip_acquisition 에 s3 update 및 트랜스코딩 상태 변경
         * 6. mubeats3ftp 에서 file 삭제
         */

        if (
            (archiveMeta.getProgramId() != null && archiveMeta.getProviderType().equalsIgnoreCase("01"))
            ||
            (archiveMeta.getAgencyId() != null && archiveMeta.getProviderType().equalsIgnoreCase("02"))
        ) {
            ClipAcquisition clipAcquisition = new ClipAcquisition();
            clipAcquisition.setProviderType(archiveMeta.getProviderType());
            clipAcquisition.setTranscodeYn(ClipState.SUCCESS);
            clipAcquisition.setUploadYn(ClipState.SUCCESS);

            Long createdId = acquisitionRepository.save(clipAcquisition).getClipMetaId();

            if (createdId != null && createdId > 0) {
                ClipMeta clipMeta = new ClipMeta();
                clipMeta.setCorporator(archiveMeta.getCorporator());
                clipMeta.setClipMetaId(createdId);
                clipMeta.setClipCategory(archiveMeta.getClipCategory());
                clipMeta.setPlayTime(archiveMeta.getPlaytime());
                clipMeta.setBroadDate(archiveMeta.getBroadDate());
                if (archiveMeta.getProgramId() != null) {
                    clipMeta.setAgencyId(null);
                    clipMeta.setProgramId(archiveMeta.getProgramId());
                }
                if (archiveMeta.getAgencyId() != 0) {
                    clipMeta.setProgramId(null);
                    clipMeta.setAgencyId(archiveMeta.getAgencyId());
                }

                Long savedItem = clipMetaRepository.save(clipMeta).getClipMetaId();

                if (createdId != savedItem) {
                    return new Result(EnumResCode.E700);
                }

                if (archiveMeta.getClipTitle() != null && archiveMeta.getClipTitle().length() > 0) {
                    ClipMetaLang clipMetaLang = new ClipMetaLang();
                    clipMetaLang.setClipMetaId(createdId);
                    clipMetaLang.setLangType(LangType.KO.name());
                    clipMetaLang.setClipTitle(archiveMeta.getClipTitle());

                    clipMetaLangRepository.save(clipMetaLang);
                }

                if (archiveMeta.getClipTitleEn() != null && archiveMeta.getClipTitleEn().length() > 0) {
                    ClipMetaLang clipMetaLang = new ClipMetaLang();
                    clipMetaLang.setClipMetaId(createdId);
                    clipMetaLang.setLangType(LangType.EN.name());
                    clipMetaLang.setClipTitle(archiveMeta.getClipTitleEn());

                    clipMetaLangRepository.save(clipMetaLang);
                }

                if (thumbImg != null) {
                    String metainfo = thumbImg.getContentType();
                    if (metainfo == null || metainfo.equalsIgnoreCase("")) {
                        metainfo = "image/jpeg";
                    }
                    String outputKey = awsUtils.uploadObjectToS3(
                        thumbImg,
                        "clipthumbnail/"+ createdId + "/" + timeStampUtil.timeStamp(),
                        metainfo
                    );
                    clipMeta.setThumbImgUrl(cloudFrontUrl + outputKey);
                    clipMeta.setClipMetaId(createdId);
                    clipMetaRepository.save(clipMeta);
                }

                if (video != null) {
                    String metainfo = video.getContentType();
                    if (metainfo == null || metainfo.equalsIgnoreCase("")) {
                        metainfo = "video/mp4";
                    }
                    String outputKey = s3Util.multipartUploadByFile(
                        video,
                        "clip/"+createdId+"/download",
                        metainfo
                    );
                    if (outputKey != null) {
                        clipAcquisition.setOriginS3Location(outputKey);
                        clipAcquisition.setClipMetaId(createdId);
                        clipAcquisition.setTranscodeYn(ClipState.NORMAL);
                        acquisitionRepository.save(clipAcquisition);

                        redisTemplate.delete("noRemainAwaitingTranscode");

                        return new Result(EnumResCode.OK);
                    }
                } else {
                    String originVideo = archiveMeta.getOriginVideo();
                    String originS3Location =
                        "clip/"+createdId+"/download" +
                        originVideo.substring(originVideo.lastIndexOf("."), originVideo.length());

                    s3Util.copyToS3FromS3(
                        archiveBucket,
                        outputBucket,
                        archiveMeta.getOriginVideo(),
                        originS3Location
                    );

                    Long s3UpdateResult = acquisitionRepository.updateOriginS3Location(createdId, originS3Location);

                    redisTemplate.delete("noRemainAwaitingTranscode");

                    if (s3UpdateResult == 0) {
                        return new Result(EnumResCode.E700);
                    }

                    s3Util.deleteFile(archiveBucket, archiveMeta.getOriginXML());
                    s3Util.deleteFile(archiveBucket, archiveMeta.getOriginXML()+".complete");
                    s3Util.deleteFile(archiveBucket, archiveMeta.getOriginVideo());
                    s3Util.deleteFile(archiveBucket, archiveMeta.getOriginVideo()+".complete");

                    return new Result(EnumResCode.OK);
                }
            }
            return new Result(EnumResCode.E998);
        } else {
            return new Result(EnumResCode.E004);
        }
    }

    @Override
    @RedisExpire(key = "acquisition:corporators", expireTime = 60L)
    @Transactional(readOnly = true)
    public List<String> getCorporators() {
        return programMetaRepository.getCorporators();
    }

    @Override
    @RedisHashExpire(key = "acquisition:programs", expireTime = 60L)
    @Transactional(readOnly = true)
    public List<ProgramSearchDTO> getPrograms(String corporator) {
        return programMetaRepository.getPrograms(corporator);
    }
}
