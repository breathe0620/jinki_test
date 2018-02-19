package kr.mubeat.cms.service.meta.album.impl;

import kr.mubeat.cms.config.Constants;
import kr.mubeat.cms.domain.meta.album.AlbumArtist;
import kr.mubeat.cms.domain.meta.album.AlbumLang;
import kr.mubeat.cms.dto.meta.album.AlbumExcelDTO;
import kr.mubeat.cms.enumerations.EnumResCode;
import kr.mubeat.cms.enumerations.LangType;
import kr.mubeat.cms.enumerations.es.EsMetaType;
import kr.mubeat.cms.domain.es.EsResult;
import kr.mubeat.cms.domain.meta.album.Album;
import kr.mubeat.cms.domain.meta.album.AlbumType;
import kr.mubeat.cms.dto.CustomPageResult;
import kr.mubeat.cms.dto.CustomSearchParam;
import kr.mubeat.cms.dto.Result;
import kr.mubeat.cms.dto.meta.album.AlbumDTO;
import kr.mubeat.cms.repository.es.EsRepository;
import kr.mubeat.cms.repository.meta.album.*;
import kr.mubeat.cms.repository.meta.artist.ArtistRepository;
import kr.mubeat.cms.service.meta.album.AlbumService;
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

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by moonkyu on 2017. 6. 9..
 */
@Service
public class AlbumServiceImpl implements AlbumService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${spring.aws.cloudfront-url}")
    private String cloudFrontUrl;

    private AlbumRepository albumRepository;
    private AlbumLangRepository albumLangRepository;
    private ArtistRepository artistRepository;
    private AlbumArtistRepository albumArtistRepository;
    private AlbumTypeRepository albumTypeRepository;
    private EsRepository esRepository;
    private AlbumOriginRepository albumOriginRepository;

    private AWSUtils awsUtils;
    private ExcelUtil excelUtil;
    private CSVUtil csvUtil;
    private TimeStampUtil timeStampUtil;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    public AlbumServiceImpl(
            AlbumRepository albumRepository,
            AlbumLangRepository albumLangRepository,
            ArtistRepository artistRepository,
            AlbumArtistRepository albumArtistRepository,
            AlbumTypeRepository albumTypeRepository,
            EsRepository esRepository,
            AWSUtils awsUtils,
            ExcelUtil excelUtil,
            CSVUtil csvUtil,
            AlbumOriginRepository albumOriginRepository,
            TimeStampUtil timeStampUtil
    ) {
        this.albumRepository = albumRepository;
        this.albumLangRepository = albumLangRepository;
        this.artistRepository = artistRepository;
        this.albumArtistRepository = albumArtistRepository;
        this.albumTypeRepository = albumTypeRepository;
        this.esRepository = esRepository;
        this.awsUtils = awsUtils;
        this.excelUtil = excelUtil;
        this.csvUtil = csvUtil;
        this.albumOriginRepository = albumOriginRepository;
        this.timeStampUtil = timeStampUtil;
    }

    @Override
    @Transactional(readOnly = true)
    public CustomPageResult getAlbumList(CustomSearchParam searchParam) {
        CustomPageResult pageResult = new CustomPageResult(searchParam);
        pageResult.setPageResult(albumRepository.getAlbumList(searchParam));
        return pageResult;
    }

    @Override
    @Transactional(readOnly = true)
    public AlbumDTO getAlbumDetail(Long albumId) {
        return albumRepository.getAlbumDetail(albumId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AlbumType> getAlbumType() {
        return albumTypeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean getCheckAlbumOriginId(Long originId){
        return albumOriginRepository.findByOriginId(originId) == null;
    }

    @Override
    @Transactional(readOnly = false)
    public Result createAlbumInfo(
            MultipartFile albumImg,
            AlbumDTO param) {

        List<Long> artistList = new ArrayList<>();

        if (param.getArtistId() != null) {
            artistList.addAll(convertArtistList(param.getArtistId()));

            if (!checkExistArtist(param.getArtistId())) {
                return new Result(EnumResCode.E002);
            }
        }

        if (param.getOriginId() != null && getNotDeletedAlbumByOriginId(param.getOriginId()).size() > 0) {
            return new Result(EnumResCode.E010);
        }

        // TODO 앨범 타입 아이디 체크 해야 함

        Album album = new Album();

        album.setOriginId(param.getOriginId());
        album.setAlbumTypeId(param.getAlbumTypeId());
        album.setPublishDt(param.getPublishDt());

        Long createId = albumRepository.save(album).getAlbumId();

        if (albumImg != null) {
//            String outputKey = awsUtils.uploadObjectToS3(
//                    albumImg,
//                    "album/" + createId + "/album_img",
//                    "image/png");
            String outputKey = awsUtils.uploadObjectToS3(
                    albumImg,
                    "album/" + createId + "/" + timeStampUtil.timeStamp(),
                    "image/png");

            if (outputKey != null) {
                album.setAlbumImgUrl(cloudFrontUrl + outputKey);
                album.setDeleteYn(Constants.FLAG_N);
                albumRepository.save(album);
            }
        }

        AlbumLang ko = new AlbumLang();
        ko.setAlbumId(createId);
        ko.setAlbumTitle(param.getAlbumTitle());
        ko.setLangType(LangType.KO.name());

        AlbumLang en = new AlbumLang();
        en.setAlbumId(createId);
        en.setAlbumTitle(param.getAlbumTitleEN());
        en.setLangType(LangType.EN.name());

        albumLangRepository.save(ko);
        albumLangRepository.save(en);

        List<AlbumArtist> albumArtistList = new ArrayList<>();
        for (Long artist : artistList) {
            albumArtistList.add(new AlbumArtist(createId, artist));
        }
        albumArtistRepository.save(albumArtistList);

        // TODO 아티스트 이름 변경
        esRepository.putMetaData(
                EsMetaType.ALBUM,
                album.getAlbumId(),
                album.getOriginId(),
                param.getAlbumTitle(),
                param.getAlbumTitleEN(),
                albumRepository.getAlbumArtists(createId),
                albumRepository.getAlbumArtistsEn(createId)
        );

        return new Result(EnumResCode.OK);
    }

    @Override
    @Transactional(readOnly = false)
    public Result updateAlbumInfo(
            MultipartFile albumImg,
            Long albumId,
            AlbumDTO param) throws Exception {

        List<Long> artistList = new ArrayList<>();

        if (param.getArtistId() != null) {
            artistList.addAll(convertArtistList(param.getArtistId()));

            if (!checkExistArtist(param.getArtistId())) {
                return new Result(EnumResCode.E002);
            }
        }

        if (param.getAlbumTypeId() != null && albumTypeRepository.findOne(param.getAlbumTypeId()) == null) {
            return new Result(EnumResCode.E503);
        }

        if (param.getOriginId() != null) {
            List<Album> albumList = getNotDeletedAlbumByOriginId(param.getOriginId());
            if (albumList.size() > 0) {
                boolean isSame = false;
                for (Album album : albumList) {
                    if (album.getAlbumId().equals(albumId)) {
                        isSame = true;
                        break;
                    }
                }
                if (!isSame) {
                    return new Result(EnumResCode.E010);
                }
            }
        }

        Album curAlbum = albumRepository.findByAlbumId(albumId);

        if (curAlbum == null)
            return new Result(EnumResCode.OK);

        Album album = new Album();

        album.setAlbumId(albumId);
        album.setOriginId(param.getOriginId());
        album.setPublishDt(param.getPublishDt());
        album.setAlbumTypeId(param.getAlbumTypeId());
        album.setAlbumImgUrl(param.getAlbumImgUrl());
        album.setDeleteYn(Constants.FLAG_N);

        if (albumRepository.save(album) == null)
            return new Result(EnumResCode.E003);

        if (albumImg != null) {
//            awsUtils.deleteObjectFromS3(
//                    "album/" + albumId + "/" + timeStampUtil.timeStamp());
            String outputKey = awsUtils.uploadObjectToS3(
                    albumImg,
                    "album/" + albumId + "/" + timeStampUtil.timeStamp(),
                    "image/png");

            if (outputKey != null) {
                album.setAlbumImgUrl(cloudFrontUrl + outputKey);
            }
//            awsUtils.invalidateCachedObjectFromCloudfront(
//                    "resize/album/" + albumId + "/album_img.png*");
        }

//        if (curAlbum.getAlbumImgUrl() != null && param.getAlbumImgUrl() == null && albumImg == null) {
////            String path = new URL(curAlbum.getAlbumImgUrl()).getPath().replaceFirst("/", "");
//            awsUtils.deleteObjectFromS3(
//                    "album/" + albumId + "/album_img");
//            awsUtils.invalidateCachedObjectFromCloudfront(
//                    "resize/album/" + albumId + "/album_img.png*");
//        }

        if (albumRepository.save(album) == null)
            return new Result(EnumResCode.E003);

        albumArtistRepository.deleteByAlbumId(albumId);
        if (param.getArtistId() != null) {
            List<AlbumArtist> albumArtistList = new ArrayList<>();
            for (Long artist : artistList) {
                albumArtistList.add(new AlbumArtist(albumId, artist));
            }
            albumArtistRepository.save(albumArtistList);
        }

        AlbumLang ko = new AlbumLang();
        ko.setAlbumId(albumId);
        ko.setAlbumTitle(param.getAlbumTitle());
        ko.setLangType(LangType.KO.name());

        AlbumLang en = new AlbumLang();
        en.setAlbumId(albumId);
        en.setAlbumTitle(param.getAlbumTitleEN());
        en.setLangType(LangType.EN.name());

        albumLangRepository.save(ko);
        albumLangRepository.save(en);

        esRepository.putMetaData(
                EsMetaType.ALBUM,
                albumId,
                album.getOriginId(),
                param.getAlbumTitle(),
                param.getAlbumTitleEN(),
                albumRepository.getAlbumArtists(albumId),
                albumRepository.getAlbumArtistsEn(albumId)
        );

        return new Result(EnumResCode.OK);
    }

    @Override
    @Transactional(readOnly = false)
    public Result deleteAlbumInfo(Long albumId) {
        Album album = albumRepository.findByAlbumId(albumId);

        if (album == null) {
            return new Result(EnumResCode.E003);
        }

        album.setDeleteYn(Constants.FLAG_Y);

        try {
            albumRepository.save(album);
        } catch (Exception e) {
            logger.error("[ERROR] deleteAlbumInfo : " + e.getMessage());
            return new Result(EnumResCode.E005);
        }

        albumArtistRepository.deleteByAlbumId(albumId);

        esRepository.deleteMetaData(EsMetaType.ALBUM, albumId);

        return new Result(EnumResCode.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public Result getAlbumMetaFromEs(String prefix) {
        EsResult esResult = esRepository.findAlbumMetaByPrefix(prefix);

        Result result = new Result(EnumResCode.OK);
        result.setData(esResult);

        return result;
    }

    @Override
    @Transactional(readOnly = false)
    public Result createAlbumType(AlbumType param) {
        AlbumType albumType;

        param.setAlbumTypeName(param.getAlbumTypeName().trim());
        try {
            albumType = albumTypeRepository.save(param);
        } catch (Exception e) {
            logger.error("[ERROR] createAlbumType : " + e.getMessage());
            return new Result(EnumResCode.E004);
        }

        Result result = new Result(EnumResCode.OK);
        result.setData(albumType);

        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Result getAndPutAllAlbumForElasticSearch() {

        List<AlbumDTO> albumDTOList = null;

        int put = 0, delete = 0;

        int page = 0;
        do {
            if (albumDTOList != null && albumDTOList.size() > 0) {
                albumDTOList.clear();
            }
            albumDTOList = albumRepository.getAllAlbumForElasticSearch(page);

            for (AlbumDTO album : albumDTOList) {
                esRepository.putMetaData(
                        EsMetaType.ALBUM,
                        album.getAlbumId(),
                        album.getOriginId(),
                        album.getAlbumTitle(),
                        album.getArtistName()
                );
            }
            logger.info("put page : " + page + ", size : " + albumDTOList.size());
            page = page + 1000;
            put = put + albumDTOList.size();
        } while (albumDTOList.size() == 1000);

        List<Long> deleteList = null;

        page = 0;
        do {
            if (deleteList != null && deleteList.size() > 0) {
                deleteList.clear();
            }
            deleteList = albumRepository.getAllDeletedAlbumForElasticSearch(page);

            for (Long id : deleteList) {
                esRepository.deleteMetaData(
                        EsMetaType.ALBUM,
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

    @Transactional(readOnly = true)
    public boolean isExistArtist(
            Long artistId) {
        return artistRepository.findByArtistId(artistId) != null;
    }

    @Transactional(readOnly = false)
    public List<Album> getNotDeletedAlbumByOriginId(Long originId) {
        return albumRepository.findAllByOriginIdAndDeleteYn(originId, Constants.FLAG_N);
    }

    private List<Long> convertArtistList(String artistIds) {
        String[] artistIdArray = artistIds.split(",");
        List<Long> idArray = new ArrayList<>();
        for (String data : artistIdArray) {
            idArray.add(Long.parseLong(data));
        }
        return idArray;
    }

    private boolean checkExistArtist(String artistIds) {
        List<Long> artistList = new ArrayList<>();

        if (artistIds != null) {
            artistList.addAll(convertArtistList(artistIds));

            for (Long artist : artistList) {
                if (!isExistArtist(artist)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public byte[] getAlbumsForExcel() {

        SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook(-1);
        Sheet sheet = sxssfWorkbook.createSheet("Original");

        int rowNum = 0;

        Row row = null;
        Cell cell = null;

        row = sheet.createRow(rowNum++);
        cell = row.createCell(0);
        cell.setCellValue("고유번호(숫자)");
        cell = row.createCell(1);
        cell.setCellValue("오리진ID(숫자)");
        cell = row.createCell(2);
        cell.setCellValue("가수");
        cell = row.createCell(3);
        cell.setCellValue("앨범이름");
        cell = row.createCell(4);
        cell.setCellValue("앨범이름-영문");
        cell = row.createCell(5);
        cell.setCellValue("발매일");
        cell = row.createCell(6);
        cell.setCellValue("생성일");
        cell = row.createCell(7);
        cell.setCellValue("삭제여부");

        List<AlbumExcelDTO> albums = albumRepository.getAlbumForExcel();
        for (AlbumExcelDTO album : albums) {
            row = sheet.createRow(rowNum++);
            cell = row.createCell(0);
            cell.setCellValue(excelUtil.escapeLongNull(album.getAlbumId()));
            cell = row.createCell(1);
            cell.setCellValue(excelUtil.escapeLongNull(album.getOriginId()));
            cell = row.createCell(2);
            cell.setCellValue(excelUtil.escapeNull(album.getArtistName()));
            cell = row.createCell(3);
            cell.setCellValue(excelUtil.escapeNull(album.getAlbumTitle()));
            cell = row.createCell(4);
            cell.setCellValue(excelUtil.escapeNull(album.getAlbumTitleEn()));
            cell = row.createCell(5);
            cell.setCellValue(excelUtil.escapeNull(album.getPublishDt()));
            cell = row.createCell(6);
            cell.setCellValue(excelUtil.escapeNull(album.getCreateDt()));
            cell = row.createCell(7);
            cell.setCellValue(excelUtil.escapeNull(album.getDeleteYn()));
        }

        return excelUtil.writeToXlsx(sxssfWorkbook);
    }

    @Override
    @Transactional(readOnly = false)
    public Result xlsxToUpdateAlbumMeta(MultipartFile file) {

        try {

            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file.getInputStream());
            XSSFSheet xssfSheet = xssfWorkbook.getSheet("Original");

            int lastRowNum = xssfSheet.getLastRowNum();

            Long albumId;
            String albumTitleEn;

            int rowNum = 1;
            for (; rowNum <= lastRowNum; rowNum++) {
                try {
                    albumId = (long)xssfSheet.getRow(rowNum).getCell(0).getNumericCellValue();
                    albumTitleEn = xssfSheet.getRow(rowNum).getCell(4).getRichStringCellValue().getString();

                    if (
                        (albumId != null && albumId > 0)
                            &&
                        (albumTitleEn != null && !albumTitleEn.equalsIgnoreCase("null") && albumTitleEn.length() > 0)
                    ) {
                        albumLangRepository.save(new AlbumLang(albumId, LangType.EN.name(), albumTitleEn));
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
