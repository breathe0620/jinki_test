package kr.mubeat.cms.service.meta.artist.impl;

import kr.mubeat.cms.config.Constants;
import kr.mubeat.cms.domain.meta.artist.*;
import kr.mubeat.cms.dto.meta.artist.ArtistExcelDTO;
import kr.mubeat.cms.enumerations.EnumResCode;
import kr.mubeat.cms.enumerations.LangType;
import kr.mubeat.cms.enumerations.es.EsMetaType;
import kr.mubeat.cms.domain.es.EsResult;
import kr.mubeat.cms.dto.CustomPageResult;
import kr.mubeat.cms.dto.CustomSearchParam;
import kr.mubeat.cms.dto.Result;
import kr.mubeat.cms.dto.meta.artist.ArtistDTO;
import kr.mubeat.cms.repository.es.EsRepository;
import kr.mubeat.cms.repository.meta.agency.AgencyRepository;
import kr.mubeat.cms.repository.meta.artist.*;
import kr.mubeat.cms.repository.meta.song.SongRepository;
import kr.mubeat.cms.service.meta.artist.ArtistService;
import kr.mubeat.cms.util.AWSUtils;
import kr.mubeat.cms.util.CSVUtil;
import kr.mubeat.cms.util.ExcelUtil;
import kr.mubeat.cms.util.TimeStampUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by moonkyu.lee on 2017. 6. 13..
 */
@Service
public class ArtistServiceImpl implements ArtistService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${spring.aws.cloudfront-url}")
    private String cloudFrontUrl;

    private ArtistRepository artistRepository;
    private ArtistLangRepository artistLangRepository;
    private ArtistActiveagesRepository artistActiveagesRepository;
    private ArtistTypeRepository artistTypeRepository;
    private EsRepository esRepository;
    private AgencyRepository agencyRepository;
    private SongRepository songRepository;
    private ArtistGroupRepository artistGroupRepository;
    private MemberArtistRepository memberArtistRepository;
    private RelativeArtistRepository relativeArtistRepository;
    private SimilarArtistRepository similarArtistRepository;
    private ArtistOriginRepository artistOriginRepository;

    private AWSUtils awsUtils;
    private ExcelUtil excelUtil;
    private CSVUtil csvUtil;
    private TimeStampUtil timeStampUtil;

    @Autowired
    public ArtistServiceImpl(
            ArtistRepository artistRepository,
            ArtistLangRepository artistLangRepository,
            ArtistActiveagesRepository artistActiveagesRepository,
            ArtistTypeRepository artistTypeRepository,
            EsRepository esRepository,
            AgencyRepository agencyRepository,
            SongRepository songRepository,
            ArtistGroupRepository artistGroupRepository,
            MemberArtistRepository memberArtistRepository,
            RelativeArtistRepository relativeArtistRepository,
            SimilarArtistRepository similarArtistRepository,
            AWSUtils awsUtils,
            ExcelUtil excelUtil,
            CSVUtil csvUtil,
            ArtistOriginRepository artistOriginRepository,
            TimeStampUtil timeStampUtil
    ) {
        this.artistRepository = artistRepository;
        this.artistLangRepository = artistLangRepository;
        this.artistActiveagesRepository = artistActiveagesRepository;
        this.artistTypeRepository = artistTypeRepository;
        this.esRepository = esRepository;
        this.agencyRepository = agencyRepository;
        this.songRepository = songRepository;
        this.artistGroupRepository = artistGroupRepository;
        this.memberArtistRepository = memberArtistRepository;
        this.relativeArtistRepository = relativeArtistRepository;
        this.similarArtistRepository = similarArtistRepository;
        this.awsUtils = awsUtils;
        this.excelUtil = excelUtil;
        this.csvUtil = csvUtil;
        this.artistOriginRepository = artistOriginRepository;
        this.timeStampUtil = timeStampUtil;
    }

    @Override
    @Transactional(readOnly = true)
    public ArtistDTO getArtistDetail(Long artistId) {
        ArtistDTO artist = artistRepository.getArtistDetail(artistId);

        if (artist == null) {
            return null;
        }

        if(artist.getAgencyId() != null)
            artist.setSameAgencyArtist(artistRepository.findSameAgencyArtist(artist.getAgencyId()));

        artist.setActiveAges(artistActiveagesRepository.findAllByArtistIdOrderByActiveAgesAsc(artistId));

        return artist;
    }

    @Override
    @Transactional(readOnly = true)
    public CustomPageResult getArtistList(CustomSearchParam searchParam) {
        CustomPageResult pageResult = new CustomPageResult(searchParam);
        pageResult.setPageResult(artistRepository.getArtistList(searchParam));
        return pageResult;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArtistType> getArtistType() {
        return artistTypeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean getCheckArtistOriginId (Long originId){
        return artistOriginRepository.findByOriginId(originId) == null;
    }

    @Override
    @Transactional(readOnly = true)
    public Result getArtistMetaFromEs(String prefix) {
        EsResult esResult = esRepository.findArtistMetaByPrefix(prefix);

        Result result = new Result(EnumResCode.OK);
        result.setData(esResult);

        return result;
    }

    @Override
    @Transactional(readOnly = false)
    public Result createArtistInfo(MultipartFile mainImg, MultipartFile profileImg, ArtistDTO param) {
        if(param.getAgencyId() != null && agencyRepository.findOne(param.getAgencyId()) == null) {
            return new Result(EnumResCode.E500);
        }

        if(param.getDebutSongId() != null && songRepository.findBySongId(param.getDebutSongId()) == null) {
            return new Result(EnumResCode.E501);
        }

        if(param.getArtistTypeId() != null && artistTypeRepository.findOne(param.getArtistTypeId()) == null) {
            return new Result(EnumResCode.E502);
        }

        if (param.getOriginId() != null && getNotDeletedArtistByOriginId(param.getOriginId()).size() > 0) {
            return new Result(EnumResCode.E011);
        }

        Result result = new Result(EnumResCode.OK);

        Artist artist = new Artist();

        artist.setArtistTypeId(param.getArtistTypeId());
        artist.setOriginId(param.getOriginId());
        artist.setDebutSongId(param.getDebutSongId());
        artist.setAgencyId(param.getAgencyId());
        artist.setDebutDt(param.getDebutDt());
        artist.setOrganizeDt(param.getOrganizeDt());
        artist.setBirthDt(param.getBirthDt());

        Long createId = artistRepository.save(artist).getArtistId();

        if(profileImg != null) {
            String outputKey = awsUtils.uploadObjectToS3(
                    profileImg,
                    "artist/" + createId + "/profile_" + timeStampUtil.timeStamp(),
                    "image/png");

            if(outputKey != null) {
                artist.setArtistProfileImgUrl(cloudFrontUrl + outputKey);
            }
        }

        if(mainImg != null) {
            String outputKey = awsUtils.uploadObjectToS3(
                    mainImg,
                    "artist/" + createId + "/main_" + timeStampUtil.timeStamp(),
                    "image/png");

            if(outputKey != null) {
                artist.setArtistMainImgUrl(cloudFrontUrl + outputKey);
            }
        }

        if(artist.getArtistMainImgUrl() != null || artist.getArtistProfileImgUrl() != null) {
            artist.setArtistId(createId);
            artist.setDeleteYn(Constants.FLAG_N);
            artistRepository.save(artist); // UPDATE (Key 값을 미리 받아올 수 있으면 좋으련만... )
        }

        if(param.getActiveAges() != null) {

            for(int i = 0; i < param.getActiveAges().size(); i++) {
                ArtistActiveages item = param.getActiveAges().get(i);
                item.setArtistId(createId);
                if (artistActiveagesRepository.save(item).getArtistId() == null) {
                    // TODO 저장된 값이 없으므로 에러 표시
                }
            }

        }

        if(param.getArtistGroup() != null) {

            for (int i = 0; i < param.getArtistGroup().size(); i++) {

                ArtistGroup item = param.getArtistGroup().get(i);
                item.setArtistId(createId);
                artistGroupRepository.save(item);

                memberArtistRepository.save(
                        new MemberArtist(
                                param.getArtistGroup().get(i).getArtistGroupId(),
                                createId
                        )
                );
            }
        }

        if(param.getMemberArtist() != null) {

            for (int i = 0; i < param.getMemberArtist().size(); i++) {

                MemberArtist item = param.getMemberArtist().get(i);
                item.setArtistId(createId);
                memberArtistRepository.save(item);
            }
        }

        if(param.getRelativeArtist() != null) {

            for (int i = 0; i < param.getRelativeArtist().size(); i++) {

                RelativeArtist item = param.getRelativeArtist().get(i);
                item.setArtistId(createId);
                relativeArtistRepository.save(item);
            }
        }

        if(param.getSimilarArtist() != null) {
            for (int i = 0; i < param.getSimilarArtist().size(); i++) {

                SimilarArtist item = param.getSimilarArtist().get(i);
                item.setArtistId(createId);
                similarArtistRepository.save(item);
            }
        }

        ArtistLang ko = new ArtistLang();
        ko.setArtistId(createId);
        ko.setArtistName(param.getArtistName());
        ko.setLangType(LangType.KO.name());

        ArtistLang en = new ArtistLang();
        en.setArtistId(createId);
        en.setArtistName(param.getArtistNameEn());
        en.setLangType(LangType.EN.name());

        artistLangRepository.save(ko);
        artistLangRepository.save(en);

        result.setData(createId);

        esRepository.putMetaData(
                EsMetaType.ARTIST,
                artist.getArtistId(),
                artist.getOriginId(),
                param.getArtistName(),
                param.getArtistNameEn(),
                null,
                null
        );

        return result;
    }

    @Override
    @Transactional(readOnly = false)
    public Result createArtistType(ArtistType param) {
        ArtistType artistType;
        param.setTypeName(param.getTypeName().trim());
        try {
            artistType = artistTypeRepository.save(param);
        } catch(Exception e) {
            logger.error("[ERROR] createArtistType : " + e.getMessage());
            return new Result(EnumResCode.E004);
        }


        Result result = new Result(EnumResCode.OK);
        result.setData(artistType);

        return result;
    }

    @Override
    @Transactional(readOnly = false)
    public Result updateArtistInfo(MultipartFile mainImg, MultipartFile profileImg, Long artistId, ArtistDTO param) throws Exception {

        if(param.getAgencyId() != null && agencyRepository.findOne(param.getAgencyId()) == null)
            return new Result(EnumResCode.E500);

        if(param.getDebutSongId() != null && songRepository.findBySongId(param.getDebutSongId()) == null)
            return new Result(EnumResCode.E501);

        if(param.getArtistTypeId() != null && artistTypeRepository.findOne(param.getArtistTypeId()) == null)
            return new Result(EnumResCode.E502);

        if (param.getOriginId() != null) {
            List<Artist> artistList = getNotDeletedArtistByOriginId(param.getOriginId());
            if (artistList.size() > 0) {
                boolean isSame = false;
                for (Artist artist : artistList) {
                    if (artist.getArtistId().equals(artistId)) {
                        isSame = true;
                        break;
                    }
                }
                if (!isSame) {
                    return new Result(EnumResCode.E011);
                }
            }
        }

        Artist curArtist = artistRepository.findByArtistId(artistId);

        if(curArtist == null)
            return new Result(EnumResCode.E002);

        Artist artist = new Artist();

        artist.setArtistId(artistId);
        artist.setArtistTypeId(param.getArtistTypeId());
        artist.setOriginId(param.getOriginId());
        artist.setDebutSongId(param.getDebutSongId());
        artist.setAgencyId(param.getAgencyId());
        artist.setDebutDt(param.getDebutDt());
        artist.setOrganizeDt(param.getOrganizeDt());
        artist.setArtistMainImgUrl(param.getMainImgUrl());
        artist.setDeleteYn(Constants.FLAG_N);
        artist.setBirthDt(param.getBirthDt());
        artist.setArtistProfileImgUrl(param.getProfileImgUrl());

        if(profileImg != null) {
//            awsUtils.deleteObjectFromS3(
//                    "artist/" + artistId + "/profile_img");
            String outputKey = awsUtils.uploadObjectToS3(
                    profileImg,
                    "artist/" + artistId + "/profile_" + timeStampUtil.timeStamp(),
                    "image/png");

            if(outputKey != null) {
                artist.setArtistProfileImgUrl(cloudFrontUrl + outputKey);
            }
//            awsUtils.invalidateCachedObjectFromCloudfront(
//                    "resize/artist/" + artistId + "/profile_img.png*");
        }

        if(mainImg != null) {
//            awsUtils.deleteObjectFromS3(
//                    "artist/" + artistId + "/main_img");
            String outputKey = awsUtils.uploadObjectToS3(
                    mainImg,
                    "artist/" + artistId + "/main_" + timeStampUtil.timeStamp(),
                    "image/png");

            if(outputKey != null) {
                artist.setArtistMainImgUrl(cloudFrontUrl + outputKey);
            }
//            awsUtils.invalidateCachedObjectFromCloudfront(
//                    "resize/artist/" + artistId + "/main_img.png*");
        }

//        // URL 을 삭제하면 이미지 삭제로 간주, 요청 URL 이 비어있고 첨부파일이 비어 있고 기존 URL 이 있다면 S3 삭제 로직을 실행한다
//        if(curArtist.getArtistMainImgUrl() != null && param.getMainImgUrl() == null && mainImg == null) {
//            //S3 DELETE
////            String path = new URL(curArtist.getArtistMainImgUrl()).getPath().replaceFirst("/", "");
//            awsUtils.deleteObjectFromS3(
//                    "artist/" + artistId + "/main_img");
//            awsUtils.invalidateCachedObjectFromCloudfront(
//                    "resize/artist/" + artistId + "/main_img.png*");
//        }
//
//        if(curArtist.getArtistProfileImgUrl() != null && param.getProfileImgUrl() == null && profileImg == null) {
////            String path = new URL(curArtist.getArtistProfileImgUrl()).getPath().replaceFirst("/", "");
//            awsUtils.deleteObjectFromS3(
//                    "artist/" + artistId + "/profile_img");
//            awsUtils.invalidateCachedObjectFromCloudfront(
//                    "resize/artist/" + artistId + "/profile_img.png*");
//        }

        if(artistRepository.save(artist) == null)
            return new Result(EnumResCode.E003);

        List<ArtistGroup> artistGroups = param.getArtistGroup();
        List<RelativeArtist> relativeArtists = param.getRelativeArtist();
        List<SimilarArtist> similarArtists = param.getSimilarArtist();
        List<Long> artistGroupList = new ArrayList<>();
        List<Long> relativeArtistList = new ArrayList<>();
        List<Long> similarArtistList = new ArrayList<>();

        for (ArtistGroup artistGroup : artistGroups) {
            artistGroupList.add(artistGroup.getArtistGroupId());
        }

        for (RelativeArtist relative : relativeArtists) {
            relativeArtistList.add(relative.getRelativeArtistId());
        }
        for (SimilarArtist similar : similarArtists) {
            similarArtistList.add(similar.getSimilarArtistId());
        }

        artistRepository.deleteArtistDeltailSubinfo(artistId, artistGroupList, relativeArtistList, similarArtistList);

        if(param.getActiveAges() != null && param.getActiveAges().size() > 0)
            artistActiveagesRepository.save(param.getActiveAges());

        if(param.getArtistGroup() != null && param.getArtistGroup().size() > 0) {
            artistGroupRepository.save(param.getArtistGroup());

            for (int i = 0; i < param.getArtistGroup().size(); i++) {
                memberArtistRepository.save(
                        new MemberArtist(
                                param.getArtistGroup().get(i).getArtistGroupId(),
                                artistId
                        )
                );
            }
        }

        if(param.getMemberArtist() != null && param.getMemberArtist().size() > 0)
            memberArtistRepository.save(param.getMemberArtist());

        if(param.getRelativeArtist() != null && param.getRelativeArtist().size() > 0) {
            relativeArtistRepository.save(param.getRelativeArtist());
            for (RelativeArtist relative : relativeArtists) {
                relativeArtistRepository.save(new RelativeArtist(relative.getRelativeArtistId(), artistId));
            }
        }

        if(param.getSimilarArtist() != null && param.getSimilarArtist().size() > 0) {
            similarArtistRepository.save(param.getSimilarArtist());
            for (SimilarArtist similar : similarArtists) {
                similarArtistRepository.save(new SimilarArtist(similar.getSimilarArtistId(), artistId));
            }
        }

        ArtistLang ko = new ArtistLang();
        ko.setArtistId(artistId);
        ko.setArtistName(param.getArtistName());
        ko.setLangType(LangType.KO.name());

        ArtistLang en = new ArtistLang();
        en.setArtistId(artistId);
        en.setArtistName(param.getArtistNameEn());
        en.setLangType(LangType.EN.name());

        artistLangRepository.save(ko);
        artistLangRepository.save(en);

        esRepository.putMetaData(
                EsMetaType.ARTIST,
                artistId,
                artist.getOriginId(),
                param.getArtistName(),
                param.getArtistNameEn(),
                null,
                null
        );

        return new Result(EnumResCode.OK);
    }

    @Override
    @Transactional(readOnly = false)
    public Result deleteArtistInfo(Long artistId) {
        Artist artist = artistRepository.findByArtistId(artistId);

        if(artist == null) {
            return new Result(EnumResCode.E002);
        }

        artist.setDeleteYn(Constants.FLAG_Y);

        try {
            artistRepository.save(artist);
        } catch(Exception e) {
            logger.error("[ERROR] deleteArtistInfo : " + e.getMessage());
            return new Result(EnumResCode.E005);
        }

        esRepository.deleteMetaData(EsMetaType.ARTIST, artistId);

        return new Result(EnumResCode.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public Result getAndPutAllArtistForElasticSearch() {

        List<ArtistDTO> artistDTOList = null;

        int put = 0, delete = 0;

        int page = 0;
        do {
            if (artistDTOList != null && artistDTOList.size() > 0) {
                artistDTOList.clear();
            }
            artistDTOList = artistRepository.getAllArtistForElasticSearch(page);

            for (ArtistDTO artist : artistDTOList) {
                esRepository.putMetaData(
                        EsMetaType.ARTIST,
                        artist.getArtistId(),
                        artist.getOriginId(),
                        artist.getArtistName(),
                        null
                );
            }
            logger.info("put page : " + page + ", size : " + artistDTOList.size());
            page = page + 1000;
            put = put + artistDTOList.size();
        } while (artistDTOList.size() == 1000);

        List<Long> deleteList = null;

        page = 0;
        do {
            if (deleteList != null && deleteList.size() > 0) {
                deleteList.clear();
            }
            deleteList = artistRepository.getAllDeletedArtistForElasticSearch(page);

            for (Long id : deleteList) {
                esRepository.deleteMetaData(
                        EsMetaType.ARTIST,
                        id
                );
            }
            logger.info("delete page : " + page + ", size : " + deleteList.size());
            page = page + 1000;
            delete = delete + deleteList.size();
        } while (deleteList.size() == 1000);

        Result result = new Result(EnumResCode.OK);
        result.setData("쓰기 : " + put + ", 삭제 : " + delete);
        return result;
    }

    @Transactional(readOnly = false)
    public List<Artist> getNotDeletedArtistByOriginId(Long originId) {
        return artistRepository.findAllByOriginIdAndDeleteYn(originId, Constants.FLAG_N);
    }

    @Override
    @Transactional(readOnly = true)
    public byte[] getArtistsForExcel() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook(-1);
        Sheet sheet = sxssfWorkbook.createSheet("Original");

        int rowNum = 0;

        Row row = null;
        Cell cell = null;

        row = sheet.createRow(rowNum);
        cell = row.createCell(0);
        cell.setCellValue("고유번호(숫자)");
        cell = row.createCell(1);
        cell.setCellValue("오리진ID(숫자)");
        cell = row.createCell(2);
        cell.setCellValue("그룹");
        cell = row.createCell(3);
        cell.setCellValue("그룹-영문");
        cell = row.createCell(4);
        cell.setCellValue("이름");
        cell = row.createCell(5);
        cell.setCellValue("이름-영문");
        cell = row.createCell(6);
        cell.setCellValue("생성일");
        cell = row.createCell(7);
        cell.setCellValue("생일");
        cell = row.createCell(8);
        cell.setCellValue("삭제여부");

        List<ArtistExcelDTO> artists = artistRepository.getArtistForExcel();
        for (ArtistExcelDTO artist : artists) {
            row = sheet.createRow(++rowNum);
            cell = row.createCell(0);
            cell.setCellValue(excelUtil.escapeLongNull(artist.getArtistId()));
            cell = row.createCell(1);
            cell.setCellValue(excelUtil.escapeLongNull(artist.getOriginId()));
            cell = row.createCell(2);
            cell.setCellValue(excelUtil.escapeNull(artist.getGroup()));
            cell = row.createCell(3);
            cell.setCellValue(excelUtil.escapeNull(artist.getGroupEn()));
            cell = row.createCell(4);
            cell.setCellValue(excelUtil.escapeNull(artist.getArtistName()));
            cell = row.createCell(5);
            cell.setCellValue(excelUtil.escapeNull(artist.getArtistNameEn()));
            cell = row.createCell(6);
            cell.setCellValue(excelUtil.escapeNull(artist.getCreateDt()));
            cell = row.createCell(7);
            cell.setCellValue(excelUtil.escapeNull(artist.getBirthDt()));
            cell = row.createCell(8);
            cell.setCellValue(excelUtil.escapeNull(artist.getDeleteYn()));
        }

        return excelUtil.writeToXlsx(sxssfWorkbook);
    }

    @Override
    @Transactional(readOnly = false)
    public Result xlsxToUpdateArtistMeta(MultipartFile file) {

        try {

            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file.getInputStream());
            XSSFSheet xssfSheet = xssfWorkbook.getSheet("Original");

            int lastRowNum = xssfSheet.getLastRowNum();

            Long artistId;
            String artistNameEn;

            int rowNum = 1;
            for (; rowNum <= lastRowNum; rowNum++) {
                try {
                    artistId = (long)xssfSheet.getRow(rowNum).getCell(0).getNumericCellValue();
                    artistNameEn = xssfSheet.getRow(rowNum).getCell(5).getRichStringCellValue().getString();

                    if (
                        (artistId != null && artistId > 0)
                            &&
                        (artistNameEn != null && !artistNameEn.equalsIgnoreCase("null") && artistNameEn.length() > 0)
                    ) {
                        artistLangRepository.save(new ArtistLang(artistId, LangType.EN.name(), artistNameEn));
                    }

                } catch (NullPointerException e) {

                }
            }

            return new Result(EnumResCode.OK);

        } catch (IOException |
                NumberFormatException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Wrong Data Format");
        }
    }
}
