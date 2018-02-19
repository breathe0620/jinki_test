package kr.mubeat.cms.service.meta.album;

import kr.mubeat.cms.domain.meta.album.AlbumType;
import kr.mubeat.cms.dto.CustomPageResult;
import kr.mubeat.cms.dto.CustomSearchParam;
import kr.mubeat.cms.dto.Result;
import kr.mubeat.cms.dto.meta.album.AlbumDTO;
import kr.mubeat.cms.dto.meta.album.AlbumExcelDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by moonkyu on 2017. 6. 9..
 */
public interface AlbumService {

    CustomPageResult getAlbumList(CustomSearchParam searchParam);

    boolean getCheckAlbumOriginId(Long originId);
    AlbumDTO getAlbumDetail(Long albumId);
    List<AlbumType> getAlbumType();
    Result createAlbumInfo(MultipartFile albumImg, AlbumDTO param);
    Result updateAlbumInfo(MultipartFile albumImg, Long albumId, AlbumDTO param) throws Exception;
    Result deleteAlbumInfo(Long albumId);
    Result getAlbumMetaFromEs(String prefix);

    Result createAlbumType(AlbumType param);

    Result getAndPutAllAlbumForElasticSearch();

    byte[] getAlbumsForExcel();
    Result xlsxToUpdateAlbumMeta(MultipartFile file);

}
