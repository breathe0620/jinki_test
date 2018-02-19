package kr.mubeat.cms.repository.meta.album;

import kr.mubeat.cms.dto.CustomSearchParam;
import kr.mubeat.cms.dto.meta.album.AlbumDTO;
import kr.mubeat.cms.dto.meta.album.AlbumExcelDTO;
import kr.mubeat.cms.enumerations.LangType;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by moonkyu.lee on 2017. 6. 8..
 */
public interface AlbumRepositoryCustom {

    Page<AlbumDTO> getAlbumList(CustomSearchParam searchParam);

    AlbumDTO getAlbumDetail(Long albumId);

    List<AlbumDTO> getAllAlbumForElasticSearch(int page);
    List<Long> getAllDeletedAlbumForElasticSearch(int page);

    String getAlbumArtists(Long albumId);

    String getAlbumArtistsEn(Long albumId);

    List<AlbumExcelDTO> getAlbumForExcel();

}