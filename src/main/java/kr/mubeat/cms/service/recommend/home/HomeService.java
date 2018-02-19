package kr.mubeat.cms.service.recommend.home;

import kr.mubeat.cms.dto.CustomPageResult;
import kr.mubeat.cms.dto.CustomSearchParam;
import kr.mubeat.cms.dto.Result;
import kr.mubeat.cms.dto.recommend.home.HomeDTO;

import java.util.List;

/**
 * Created by moonkyu.lee on 2017. 10. 31..
 */
public interface HomeService {

    List<HomeDTO> getHomes();
    Result saveHome(List<HomeDTO> homeDTOList);
    CustomPageResult getSearchClipList(CustomSearchParam searchParam);

}
