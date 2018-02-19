package kr.mubeat.cms.repository.meta.artist;

import kr.mubeat.cms.dto.CustomSearchParam;
import kr.mubeat.cms.dto.meta.artist.ArtistDTO;
import kr.mubeat.cms.dto.meta.artist.ArtistExcelDTO;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by doohwan.yoo on 2017. 5. 15..
 */
public interface ArtistRepositoryCustom {
    Page<ArtistDTO> getArtistList(CustomSearchParam searchParam);

    ArtistDTO getArtistDetail(Long artistId);
    Long removeAgencyByAgencyId(Integer agencyId);
    List<ArtistDTO> findSameAgencyArtist(Integer agencyId);
    void deleteArtistDeltailSubinfo(Long artistId, List<Long> artistGroupList, List<Long> relativeArtistList, List<Long> similarArtistList);

    List<ArtistDTO> getAllArtistForElasticSearch(int page);
    List<Long> getAllDeletedArtistForElasticSearch(int page);

    List<ArtistExcelDTO> getArtistForExcel();

}
