package kr.mubeat.cms.repository.recommend.home;

import kr.mubeat.cms.dto.CustomSearchParam;
import kr.mubeat.cms.dto.recommend.home.ClipDTO;
import kr.mubeat.cms.dto.recommend.home.HomeDTO;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by moonkyu.lee on 2017. 11. 3..
 */
public interface HomeRepositoryCustom {

    Page<ClipDTO> getSearchClipList(CustomSearchParam searchParam);
    List<HomeDTO> getHomes();
    void homeSave(Integer recommendHomeId, Long clipMetaId);

}
