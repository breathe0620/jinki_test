package kr.mubeat.cms.service.meta.clip.impl;

import kr.mubeat.cms.domain.meta.clip.ClipArtist;
import kr.mubeat.cms.domain.meta.clip.ClipMeta;
import kr.mubeat.cms.domain.meta.clip.ClipMetaLang;
import kr.mubeat.cms.domain.meta.clip.ClipSong;
import kr.mubeat.cms.dto.CustomPageResult;
import kr.mubeat.cms.dto.CustomSearchParam;
import kr.mubeat.cms.dto.Result;
import kr.mubeat.cms.dto.meta.acquisition.ClipMetaDTO;
import kr.mubeat.cms.dto.meta.clip.*;
import kr.mubeat.cms.enumerations.EnumResCode;
import kr.mubeat.cms.enumerations.LangType;
import kr.mubeat.cms.enumerations.es.EsMetaType;
import kr.mubeat.cms.repository.dynamodb.ClipLikeRepository;
import kr.mubeat.cms.repository.es.EsRepository;
import kr.mubeat.cms.repository.meta.clip.*;
import kr.mubeat.cms.repository.meta.program.ProgramMetaRepository;
import kr.mubeat.cms.service.meta.artist.ArtistService;
import kr.mubeat.cms.service.meta.clip.ClipService;
import kr.mubeat.cms.service.meta.song.SongService;
import kr.mubeat.cms.util.CSVUtil;
import kr.mubeat.cms.util.ElasticsearchUtil;
import kr.mubeat.cms.util.ExcelUtil;
import kr.mubeat.cms.util.SearchWordUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by moonkyu.lee on 2017. 6. 20..
 */
@Service
public class ClipServiceImpl implements ClipService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private ClipRepository clipRepository;
    private ClipArtistRepository clipArtistRepository;
    private ClipSongRepository clipSongRepository;
    private ClipMetaRepository clipMetaRepository;
    private ClipMetaLangRepository clipMetaLangRepository;
    private EsRepository esRepository;
    private ClipLikeRepository clipLikeRepository;

    private ElasticsearchUtil elasticsearchUtil;
    private ExcelUtil excelUtil;

    @Autowired
    public ClipServiceImpl(
            ClipRepository clipRepository,
            ClipArtistRepository clipArtistRepository,
            ClipSongRepository clipSongRepository,
            ClipMetaRepository clipMetaRepository,
            ClipMetaLangRepository clipMetaLangRepository,
            EsRepository esRepository,
            ClipLikeRepository clipLikeRepository,
            ElasticsearchUtil elasticsearchUtil,
            ExcelUtil excelUtil
    ) {
        this.clipRepository = clipRepository;
        this.clipArtistRepository = clipArtistRepository;
        this.clipSongRepository = clipSongRepository;
        this.clipMetaRepository = clipMetaRepository;
        this.clipMetaLangRepository = clipMetaLangRepository;
        this.esRepository = esRepository;
        this.clipLikeRepository = clipLikeRepository;
        this.elasticsearchUtil = elasticsearchUtil;
        this.excelUtil = excelUtil;
    }

    @Override
    @Transactional(readOnly = true)
    public CustomPageResult getClipList(CustomSearchParam searchParam) {
        CustomPageResult pageResult = new CustomPageResult(searchParam);
        pageResult.setPageResult(clipRepository.getClipList(searchParam));
        return pageResult;
    }

    @Override
    @Transactional(readOnly = true)
    public byte[] getClipsForExcel(CustomSearchParam searchParam) {
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
        cell.setCellValue("방송사");
        cell = row.createCell(2);
        cell.setCellValue("프로그램");
        cell = row.createCell(3);
        cell.setCellValue("프로그램-영문");
        cell = row.createCell(4);
        cell.setCellValue("방송장르");
        cell = row.createCell(5);
        cell.setCellValue("클립명");
        cell = row.createCell(6);
        cell.setCellValue("클립명-영문");
        cell = row.createCell(7);
        cell.setCellValue("클립분류");
        cell = row.createCell(8);
        cell.setCellValue("수정일");
        cell = row.createCell(9);
        cell.setCellValue("노출여부");
        cell = row.createCell(10);
        cell.setCellValue("영문명 가이드");

        List<ClassifyDTO> classifys = clipRepository.getClassify();
        List<ClipExcelDTO> clips = clipRepository.getClipsForExcel(searchParam);

        for (ClipExcelDTO clip : clips) {
            String classify = "";
            row = sheet.createRow(++rowNum);
            cell = row.createCell(0);
            cell.setCellValue(excelUtil.escapeLongNull(clip.getClipMetaId()));
            cell = row.createCell(1);
            cell.setCellValue(excelUtil.escapeNull(clip.getCorporator()));
            cell = row.createCell(2);
            cell.setCellValue(excelUtil.escapeNull(clip.getProgramTitle()));
            cell = row.createCell(3);
            cell.setCellValue(excelUtil.escapeNull(clip.getProgramTitleEn()));
            cell = row.createCell(4);
            cell.setCellValue(excelUtil.escapeNull(getClipGenre(clip.getClipCategory())));
            cell = row.createCell(5);
            cell.setCellValue(excelUtil.escapeNull(clip.getClipTitle()));
            cell = row.createCell(6);
            cell.setCellValue(excelUtil.escapeNull(clip.getClipTitleEn()));
            cell = row.createCell(7);
            for (ClassifyDTO data : classifys) {
                if (data.getClipClassify() == clip.getClipClassify()) {
                    classify = data.getName();
                }
            }
            cell.setCellValue(excelUtil.escapeNull(classify));
            cell = row.createCell(8);
            cell.setCellValue(excelUtil.escapeNull(simpleDateFormat.format(clip.getModifyDt())));
            cell = row.createCell(9);
            cell.setCellValue(excelUtil.escapeNull(getClipEnableState(clip.getEnableYn())));
            cell = row.createCell(10);
            cell.setCellValue(excelUtil.escapeNull(clip.getClipTitleEnGuide()));
        }

        return excelUtil.writeToExcel(sxssfWorkbook);
    }

    @Override
    @Transactional(readOnly = false)
    public Result xlsxToUpdateClipMeta(MultipartFile file) {

        logger.info("xlsxToUpdateClipMeta : " + file.getName());

        HashMap<Integer, String> totalErrorList = new HashMap<>();

        logger.info("Open file...");
        XSSFWorkbook xssfWorkbook = null;
        try {
            logger.info("excel file : start..");
            xssfWorkbook = new XSSFWorkbook(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            Result result = new Result(EnumResCode.E999);
            result.setMsg(e.getMessage());
            logger.info("excel file : I/O Exception error");
            return result;
        }

        logger.info("Get main sheet...");
        XSSFSheet xssfSheet = xssfWorkbook.getSheet("Original");

        int lastRowNum = xssfSheet.getLastRowNum();
        logger.info("Total rows : " + lastRowNum);

        Long clipMetaId;
        String clipTitleEn;

        int rowNum = 1;
        for (; rowNum <= lastRowNum; rowNum++) {
            try {
                clipMetaId = (long) xssfSheet.getRow(rowNum).getCell(0).getNumericCellValue();
                clipTitleEn = xssfSheet.getRow(rowNum).getCell(6).getRichStringCellValue().getString();

                if (
                        (clipMetaId > 0)
                                &&
                                (clipTitleEn != null && !clipTitleEn.equalsIgnoreCase("null") && clipTitleEn.length() > 0)
                        ) {
                    logger.info(clipMetaId + " | " + clipTitleEn);
                    clipMetaLangRepository.save(new ClipMetaLang(clipMetaId, LangType.EN.name(), clipTitleEn));
                }
            } catch (NullPointerException | NumberFormatException | IllegalStateException e) {
                logger.info("excel file : insert error");
                logger.info("insert error : "+ (rowNum + 1) + ", "+ e.getMessage());
                totalErrorList.put(rowNum+1, e.getMessage());
            }
        }
        Result result = new Result(EnumResCode.OK);
        result.setData(totalErrorList);
        logger.info("excel file : insert complete");
        return result;

    }

    @Override
    @Transactional(readOnly = false)
    public Result updateClipInfo(Long clipMetaId, ClipDTO param) {

        // TODO param 값중 enableYn 이 Y 로 넘어온 경우 아티스트와 상세 카테고리를 확인한 뒤 반영 한다.
        if (param.getEnableYn().equalsIgnoreCase("y")) {
            if (!(param.getClipClassify() > 0
                    &&
                param.getArtistList() != null
                    &&
                param.getArtistList().size() > 0)) {
                return new Result(EnumResCode.E013);
            }
        } else {
            if (clipMetaRepository.findOne(clipMetaId).getEnableYn().equalsIgnoreCase("Y")) {
                clipLikeRepository.deleteItemByClipMetaId(clipMetaId);
            }
            boolean result = elasticsearchUtil.deleteItem(
//                    "/meta/clip",
                    "/clip/doc",
                    clipMetaId
            );
            if (!result) {
                JSONObject object = elasticsearchUtil.getById(
                        "clip",
                        String.valueOf(clipMetaId)
                );
                if (object != null) {
                    throw new RuntimeException("ElasticSearch Write Fail");
                }
            }
        }

        clipArtistRepository.deleteByclipMetaId(clipMetaId);
        clipSongRepository.deleteByclipMetaId(clipMetaId);

        int i = 0;
        int size = param.getArtistList().size();
        if (param.getArtistList() != null && size > 0) {
            List<ClipArtist> items = new ArrayList<>();
            for (; i < size; i++) {
                ClipArtist item = new ClipArtist();
                item.setArtistId(param.getArtistList().get(i).getArtistId());
                item.setClipMetaId(clipMetaId);
                items.add(item);
            }
            clipArtistRepository.save(items);
        }

        i = 0;
        size = param.getSongList().size();
        if (param.getSongList() != null && size > 0) {
            List<ClipSong> items = new ArrayList<>();
            for (; i < size; i++) {
                ClipSong item = new ClipSong();
                item.setSongId(param.getSongList().get(i).getSongId());
                item.setClipMetaId(clipMetaId);
                items.add(item);
            }
            clipSongRepository.save(items);
        }

        ClipMeta clipMeta = new ClipMeta();
        clipMeta.setClipMetaId(clipMetaId);
        clipMeta.setEnableYn(param.getEnableYn());
        clipMeta.setIsoriginal(param.getIsoriginal());
        clipMeta.setClipClassify(param.getClipClassify());

        if (clipRepository.updateClipInfo(clipMetaId, clipMeta) > 0) {

            if (param.getClipTitle() != null) {
                ClipMetaLang ko = new ClipMetaLang();
                ko.setClipMetaId(clipMetaId);
                ko.setClipTitle(param.getClipTitle().trim());
                ko.setLangType(LangType.KO.name());

                clipMetaLangRepository.save(ko);
            }

                ClipMetaLang en = new ClipMetaLang();
                if (param.getClipTitleEn() != null) {
                en.setClipMetaId(clipMetaId);
                en.setClipTitle(param.getClipTitleEn().trim());
                en.setLangType(LangType.EN.name());

                clipMetaLangRepository.save(en);
            }

            boolean regist = false;
            if (param.getEnableYn().equalsIgnoreCase("y")) {
                 regist = elasticsearchUtil.putItem(
//                        "/meta/clip",
                         "clip/doc",
                        clipMetaId,
                        generateClipPayload(clipMetaId)
                );
            } else {
                regist = true;
            }
            if (!regist) {
                throw new RuntimeException("ElasticSearch Write Fail");
            }

            return new Result(EnumResCode.OK);
        } else {
            return new Result(EnumResCode.E003);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ClipDTO getClipDetail(Long metaId) {
        return clipRepository.getClipDetail(metaId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClassifyDTO> getClassify() {
        return clipRepository.getClassify();
    }

    @Override
    @Transactional(readOnly = true)
    public TranscodeDTO getTranscodeStates() {
        return clipRepository.getTranscodeStates();
    }

    private String getClipGenre(String genre) {
        if (genre.equalsIgnoreCase("02")) {
            return "예능";
        } else if (genre.equalsIgnoreCase("03")) {
            return "음악";
        }
        return "";
    }

    private String getClipEnableState(String enableYn) {
        if (enableYn.equalsIgnoreCase("Y")) {
            return "노출";
        } else if (enableYn.equalsIgnoreCase("T")) {
            return "폐기";
        } else if (enableYn.equalsIgnoreCase("N")) {
            return "미노출";
        } else if (enableYn.equalsIgnoreCase("R")) {
            return "보류";
        }
        return "";
    }

    @Override
    @Transactional(readOnly = true)
    public Result getAndPutAllClipForElasticSearch() {

        List<ClipEsDTO> clipEsDTOList = null;

        int put = 0, delete = 0;

        int page = 0;
        do {
            if (clipEsDTOList != null && clipEsDTOList.size() > 0) {
                clipEsDTOList.clear();
            }
            clipEsDTOList = clipRepository.getAllClipForElasticSearch(page);

            for (ClipEsDTO clip : clipEsDTOList) {
                elasticsearchUtil.putItem(
                        "/meta/clip",
                        clip.getClipMetaId(),
                        generateClipPayload(clip)
                );
            }
            logger.info("put page : " + page + ", size : " + clipEsDTOList.size());
            page = page + 1000;
            put = put + clipEsDTOList.size();
        } while (clipEsDTOList.size() == 1000);

        List<Long> deleteList = null;

        page = 0;
        do {
            if (deleteList != null && deleteList.size() > 0) {
                deleteList.clear();
            }
            deleteList = clipRepository.getAllDeletedClipForElasticSearch(page);

            for (Long id : deleteList) {
                esRepository.deleteMetaData(
                        EsMetaType.CLIP,
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

    private String generateClipPayload(Long clipMetaId) {

        List<ClipEsDTO> clipDatas = clipRepository.getClipForElasticSearch(clipMetaId);
        if (clipDatas.size() != 1) {
            // TODO Exception 을 던지는 것이 맞는듯 하다.
            return null;
        }
        ClipEsDTO clipData = clipDatas.get(0);

        return this.generateClipPayload(clipData);

    }

    private String generateClipPayload(ClipEsDTO clipData) {
        String title = clipData.getClipTitle();
        title = title.replaceAll("\"", "\\\\\"");
        String titleEn = clipData.getClipTitleEn();
        titleEn = titleEn.replaceAll("\"", "\\\\\"");

        String artistSong = generateArtistSong(clipData.getArtists(), clipData.getSongs());
        String artistSongEn = generateArtistSong(clipData.getArtistsEn(), clipData.getSongsEn());

        return "{\"title\":\""+checkNull(title)+"\""+
                ",\"title_en\":\""+checkNull(titleEn)+"\""+
                ",\"keyword\":\""+checkNull(title.replaceAll(" ",""))+"\""+
                ",\"keyword_ex\":\""+checkNull(artistSong)+"\"" +
                ",\"keyword_en\":\""+checkNull(titleEn.replaceAll(" ",""))+"\""+
                ",\"keyword_en_ex\":\""+checkNull(artistSongEn)+"\"" +
                "}";
    }

    private String generateArtistSong(String artist, String song) {
        StringBuilder artistSong = new StringBuilder();
        if (artist != null && !artist.equalsIgnoreCase("null")
            &&
            song != null && !song.equalsIgnoreCase("null")) {

            artist = artist.replaceAll(" ", "");
            String[] artists = artist.split("---");
            for (String data : artists) {
                if (artistSong.toString().length() > 0) {
                    artistSong.append(" ");
                }
                artistSong.append(data);
                if (data.contains(".")) {
                    artistSong.append(" "+data.replaceAll("\\.",""));
                }
            }

            song = song.replaceAll(" ", "");
            String[] songs = song.split("---");
            for (String data : songs) {
                if (artistSong.toString().length() > 0) {
                    artistSong.append(" ");
                }
                artistSong.append(data);
            }
        }
        if (artistSong.toString().equalsIgnoreCase("---")) {
            return "";
        }
        return artistSong.toString().replaceAll("\"", "\\\\\"").replaceAll("---", " ");
    }

    private String checkNull(String data) {
        if (data == null || data.equalsIgnoreCase("null") || data.indexOf("null") == 0) {
            return "";
        }
        return data;
    }
}
