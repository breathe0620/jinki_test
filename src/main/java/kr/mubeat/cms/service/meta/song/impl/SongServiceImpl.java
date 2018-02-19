package kr.mubeat.cms.service.meta.song.impl;

import kr.mubeat.cms.config.Constants;
import kr.mubeat.cms.domain.meta.song.SongArtist;
import kr.mubeat.cms.domain.meta.song.SongLang;
import kr.mubeat.cms.dto.meta.song.SongExcelDTO;
import kr.mubeat.cms.enumerations.EnumResCode;
import kr.mubeat.cms.enumerations.LangType;
import kr.mubeat.cms.enumerations.es.EsMetaType;
import kr.mubeat.cms.domain.es.EsResult;
import kr.mubeat.cms.domain.meta.song.Song;
import kr.mubeat.cms.domain.meta.song.SongGenre;
import kr.mubeat.cms.dto.CustomPageResult;
import kr.mubeat.cms.dto.CustomSearchParam;
import kr.mubeat.cms.dto.Result;
import kr.mubeat.cms.dto.meta.song.SongDTO;
import kr.mubeat.cms.repository.es.EsRepository;
import kr.mubeat.cms.repository.meta.album.AlbumRepository;
import kr.mubeat.cms.repository.meta.artist.ArtistRepository;
import kr.mubeat.cms.repository.meta.song.*;
import kr.mubeat.cms.service.meta.song.SongService;
import kr.mubeat.cms.util.CSVUtil;
import kr.mubeat.cms.util.ExcelUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
 * Created by moonkyu.lee on 2017. 6. 13..
 */
@Service
public class SongServiceImpl implements SongService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private SongRepository songRepository;
    private SongLangRepository songLangRepository;
    private AlbumRepository albumRepository;
    private SongArtistRepository songArtistRepository;
    private ArtistRepository artistRepository;
    private SongGenreRepository songGenreRepository;
    private EsRepository esRepository;
    private SongOriginRepository songOriginRepository;

    private ExcelUtil excelUtil;
    private CSVUtil csvUtil;

    @Autowired
    public SongServiceImpl(
            SongRepository songRepository,
            SongLangRepository songLangRepository,
            AlbumRepository albumRepository,
            SongArtistRepository songArtistRepository,
            ArtistRepository artistRepository,
            SongGenreRepository songGenreRepository,
            EsRepository esRepository,
            ExcelUtil excelUtil,
            CSVUtil csvUtil,
            SongOriginRepository songOriginRepository
    ) {
        this.songRepository = songRepository;
        this.songLangRepository = songLangRepository;
        this.albumRepository = albumRepository;
        this.songArtistRepository = songArtistRepository;
        this.artistRepository = artistRepository;
        this.songGenreRepository = songGenreRepository;
        this.esRepository = esRepository;
        this.excelUtil = excelUtil;
        this.csvUtil = csvUtil;
        this.songOriginRepository = songOriginRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public CustomPageResult getSongList(CustomSearchParam searchParam) {
        CustomPageResult pageResult = new CustomPageResult(searchParam);
        pageResult.setPageResult(songRepository.getSongList(searchParam));
        return pageResult;
    }

    @Override
    @Transactional(readOnly = true)
    public SongDTO getSongDetail(Long songId) {
        return songRepository.getSongDetail(songId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SongGenre> getSongGenre() {
        return songGenreRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean getCheckSongOriginId(Long originId){
        return songOriginRepository.findByOriginId(originId) == null;
    }

    @Override
    @Transactional(readOnly = true)
    public Result getSongMetaFromEs(String prefix) {
        EsResult esResult = esRepository.findSongMetaByPrefix(prefix);

        Result result = new Result(EnumResCode.OK);
        result.setData(esResult);

        return result;
    }

    @Override
    @Transactional(readOnly = false)
    public Result createSongInfo(SongDTO param) {

        List<Long> artistList = new ArrayList<>();

        if (param.getArtistId() != null) {
            artistList.addAll(convertArtistList(param.getArtistId()));

            if (!checkExistArtist(param.getArtistId())) {
                return new Result(EnumResCode.E002);
            }
        }

        if (param.getOriginId() != null && getNotDeletedSongByOriginId(param.getOriginId()).size() > 0) {
            return new Result(EnumResCode.E012);
        }

        Song song = new Song();

        song.setGenreId(param.getGenreId());
        song.setOriginId(param.getOriginId());
        song.setAlbumId(param.getAlbumId());
        song.setLyric(param.getLyric());

        Long createdId = songRepository.save(song).getSongId();

        Result result = new Result(EnumResCode.OK);
        result.setData(createdId);

        List<SongArtist> songArtistList = new ArrayList<>();
        for (Long artist : artistList) {
            songArtistList.add(new SongArtist(createdId, artist));
        }
        songArtistRepository.save(songArtistList);

        SongLang ko = new SongLang();
        ko.setSongId(createdId);
        ko.setSongName(param.getSongName());
        ko.setLangType(LangType.KO.name());

        SongLang en = new SongLang();
        en.setSongId(createdId);
        en.setSongName(param.getSongNameEn());
        en.setLangType(LangType.EN.name());

        songLangRepository.save(ko);
        songLangRepository.save(en);

        esRepository.putMetaData(
                EsMetaType.SONG,
                song.getSongId(),
                song.getOriginId(),
                param.getSongName(),
                param.getSongNameEn(),
                songRepository.getSongArtists(createdId),
                songRepository.getSongArtistsEn(createdId)
        );

        return result;
    }

    @Override
    @Transactional(readOnly = false)
    public Result createSongGenre(SongGenre param) {
        SongGenre songGenre;
        param.setGenreName(param.getGenreName().trim());
        try {
            songGenre = songGenreRepository.save(param);
        } catch(Exception e) {
            logger.error("[ERROR] createSongGenre : " + e.getMessage());
            return new Result(EnumResCode.E004);
        }

        Result result = new Result(EnumResCode.OK);
        result.setData(songGenre);

        return result;
    }

    @Override
    @Transactional(readOnly = false)
    public Result updateSongInfo(
            Long songId,
            SongDTO param
    ) {

        List<Long> artistList = new ArrayList<>();

        if (param.getArtistId() != null) {
            artistList.addAll(convertArtistList(param.getArtistId()));

            if (!checkExistArtist(param.getArtistId())) {
                return new Result(EnumResCode.E002);
            }
        }

        if (param.getOriginId() != null) {
            List<Song> songList = getNotDeletedSongByOriginId(param.getOriginId());
            if (songList.size() > 0) {
                boolean isSame = false;
                for (Song song : songList) {
                    if (song.getSongId().equals(songId)) {
                        isSame = true;
                        break;
                    }
                }
                if (!isSame) {
                    return new Result(EnumResCode.E012);
                }
            }
        }

        Song curSong = songRepository.findBySongId(songId);

        if(curSong == null) {
            return new Result(EnumResCode.E001);
        }

        Song song = new Song();
        song.setGenreId(param.getGenreId());
        song.setOriginId(param.getOriginId());
        song.setAlbumId(param.getAlbumId());
        song.setLyric(param.getLyric());

        if(songRepository.updateSongInfo(songId, song) <= 0) {
            return new Result(EnumResCode.E003);
        }

        songArtistRepository.deleteBySongId(songId);
        if (param.getArtistId() != null) {
            List<SongArtist> songArtistList = new ArrayList<>();
            for (Long artist : artistList) {
                songArtistList.add(new SongArtist(songId, artist));
            }
            songArtistRepository.save(songArtistList);
        }

        SongLang ko = new SongLang();
        ko.setSongId(songId);
        ko.setSongName(param.getSongName());
        ko.setLangType(LangType.KO.name());

        SongLang en = new SongLang();
        en.setSongId(songId);
        en.setSongName(param.getSongNameEn());
        en.setLangType(LangType.EN.name());

        songLangRepository.save(ko);
        songLangRepository.save(en);

        esRepository.putMetaData(
                EsMetaType.SONG,
                songId,
                song.getOriginId(),
                param.getSongName(),
                param.getSongNameEn(),
                songRepository.getSongArtists(songId),
                songRepository.getSongArtistsEn(songId)
        );

        return new Result(EnumResCode.OK);
    }

    @Override
    @Transactional(readOnly = false)
    public Result deleteSongInfo(Long songId) {
        Song song = songRepository.findBySongId(songId);

        if(song == null) {
            return new Result(EnumResCode.E003);
        }

        song.setDeleteYn("Y");

        try {
            songRepository.save(song);
        } catch(Exception e) {
            logger.error("[ERROR] deleteSongInfo : " + e.getMessage());
            return new Result(EnumResCode.E005);
        }

        songArtistRepository.deleteBySongId(songId);

        esRepository.deleteMetaData(EsMetaType.SONG, songId);

        return new Result(EnumResCode.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public Result getAndPutAllSongForElasticSearch() {

        List<SongDTO> songDTOList = null;

        int put = 0, delete = 0;

        int page = 0;
        do {
            if (songDTOList != null && songDTOList.size() > 0) {
                songDTOList.clear();
            }
            songDTOList = songRepository.getAllSongForElasticSearch(page);

            for (SongDTO song : songDTOList) {
                esRepository.putMetaData(
                        EsMetaType.SONG,
                        song.getSongId(),
                        song.getOriginId(),
                        song.getSongName(),
                        song.getArtistName()
                );
            }
            logger.info("put page : " + page + ", size : " + songDTOList.size());
            page = page + 1000;
            put = put + songDTOList.size();
        } while (songDTOList.size() == 1000);

        List<Long> deleteList = null;

        page = 0;
        do {
            if (deleteList != null && deleteList.size() > 0) {
                deleteList.clear();
            }
            deleteList = songRepository.getAllDeletedSongForElasticSearch(page);

            for (Long id : deleteList) {
                esRepository.deleteMetaData(
                        EsMetaType.SONG,
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
    public boolean isExistArtist(Long artistId) {
        return artistRepository.findByArtistId(artistId) != null;
    }

    @Transactional(readOnly = false)
    public List<Song> getNotDeletedSongByOriginId(Long originId) {
        // TODO 체크 해야 함
        return songRepository.findAllByOriginIdAndDeleteYn(originId, Constants.FLAG_N);
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
    public byte[] getSongsForExcel() {
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
        cell.setCellValue("곡이름");
        cell = row.createCell(3);
        cell.setCellValue("곡이름-영문");
        cell = row.createCell(4);
        cell.setCellValue("앨범이름");
        cell = row.createCell(5);
        cell.setCellValue("아티스트이름");
        cell = row.createCell(6);
        cell.setCellValue("생성일");
        cell = row.createCell(7);
        cell.setCellValue("삭제여부");

        List<SongExcelDTO> songs = songRepository.getSongForExcel();
        for (SongExcelDTO song : songs) {
            row = sheet.createRow(++rowNum);
            cell = row.createCell(0);
            cell.setCellValue(excelUtil.escapeLongNull(song.getSongId()));
            cell = row.createCell(1);
            cell.setCellValue(excelUtil.escapeLongNull(song.getOriginId()));
            cell = row.createCell(2);
            cell.setCellValue(excelUtil.escapeNull(song.getSongName()));
            cell = row.createCell(3);
            cell.setCellValue(excelUtil.escapeNull(song.getSongNameEn()));
            cell = row.createCell(4);
            cell.setCellValue(excelUtil.escapeNull(song.getAlbumTitle()));
            cell = row.createCell(5);
            cell.setCellValue(excelUtil.escapeNull(song.getArtistName()));
            cell = row.createCell(6);
            cell.setCellValue(excelUtil.escapeNull(song.getCreateDt()));
            cell = row.createCell(7);
            cell.setCellValue(excelUtil.escapeNull(song.getDeleteYn()));
        }

        return excelUtil.writeToExcel(sxssfWorkbook);
    }

    @Override
    @Transactional(readOnly = false)
    public Result xlsxToUpdateSongLangMeta(MultipartFile file) {

        try {

            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file.getInputStream());
            XSSFSheet xssfSheet = xssfWorkbook.getSheet("Original");

            int lastRowNum = xssfSheet.getLastRowNum();

            Long songId;
            String songTitleEn;

            int rowNum = 1;
            for (; rowNum <= lastRowNum; rowNum++) {
                try {
                    songId = (long)xssfSheet.getRow(rowNum).getCell(0).getNumericCellValue();
                    songTitleEn = xssfSheet.getRow(rowNum).getCell(3).getRichStringCellValue().getString();

                    if (
                        (songId != null && songId > 0)
                            &&
                        (songTitleEn != null && !songTitleEn.equalsIgnoreCase("null") && songTitleEn.length() > 0)
                    ) {
                        songLangRepository.save(new SongLang(songId, LangType.EN.name(), songTitleEn));
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
