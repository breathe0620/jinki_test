package kr.mubeat.cms.service.meta.song;

import kr.mubeat.cms.domain.meta.song.SongGenre;
import kr.mubeat.cms.dto.CustomPageResult;
import kr.mubeat.cms.dto.CustomSearchParam;
import kr.mubeat.cms.dto.Result;
import kr.mubeat.cms.dto.meta.song.SongDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by moonkyu.lee on 2017. 6. 13..
 */
public interface SongService {

    CustomPageResult getSongList(CustomSearchParam searchParam);

    boolean getCheckSongOriginId(Long originId);
    SongDTO getSongDetail(Long songId);
    List<SongGenre> getSongGenre();
    Result getSongMetaFromEs(String prefix);
    Result createSongInfo(SongDTO param);
    Result createSongGenre(SongGenre param);
    Result updateSongInfo(Long songId, SongDTO param);
    Result deleteSongInfo(Long songId);

    Result getAndPutAllSongForElasticSearch();

    byte[] getSongsForExcel();
    Result xlsxToUpdateSongLangMeta(MultipartFile file);

}
