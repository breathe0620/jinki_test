package kr.mubeat.cms.repository.meta.song;

import kr.mubeat.cms.domain.meta.song.Song;
import kr.mubeat.cms.dto.CustomSearchParam;
import kr.mubeat.cms.dto.meta.song.SongDTO;
import kr.mubeat.cms.dto.meta.song.SongExcelDTO;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by doohwan.yoo on 2017. 5. 10..
 */
public interface SongRepositoryCustom {
    Page<SongDTO> getSongList(CustomSearchParam searchParam);

    SongDTO getSongDetail(Long songId);
    Long updateSongInfo(Long songId, Song param);

    List<SongDTO> getAllSongForElasticSearch(int page);
    List<Long> getAllDeletedSongForElasticSearch(int page);

    String getSongArtists(Long songId);
    String getSongArtistsEn(Long songId);

    List<SongExcelDTO> getSongForExcel();
}
