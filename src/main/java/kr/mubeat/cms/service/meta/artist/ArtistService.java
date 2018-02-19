package kr.mubeat.cms.service.meta.artist;

import kr.mubeat.cms.domain.meta.artist.ArtistType;
import kr.mubeat.cms.dto.CustomPageResult;
import kr.mubeat.cms.dto.CustomSearchParam;
import kr.mubeat.cms.dto.Result;
import kr.mubeat.cms.dto.meta.artist.ArtistDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by moonkyu.lee on 2017. 6. 13..
 */
public interface ArtistService {

    CustomPageResult getArtistList(CustomSearchParam searchParam);

    boolean getCheckArtistOriginId(Long originId);
    ArtistDTO getArtistDetail(Long artistId);
    List<ArtistType> getArtistType();
    Result getArtistMetaFromEs(String prefix);
    Result createArtistInfo(MultipartFile mainImg, MultipartFile profileImg, ArtistDTO param);
    Result createArtistType(ArtistType param);
    Result updateArtistInfo(MultipartFile mainImg, MultipartFile profileImg, Long artistId, ArtistDTO param) throws Exception;
    Result deleteArtistInfo(Long artistId);

    Result getAndPutAllArtistForElasticSearch();

    byte[] getArtistsForExcel();
    Result xlsxToUpdateArtistMeta(MultipartFile file);

}
