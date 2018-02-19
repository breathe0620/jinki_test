package kr.mubeat.cms.service.recommend.home.impl;

import kr.mubeat.cms.dto.CustomPageResult;
import kr.mubeat.cms.dto.CustomSearchParam;
import kr.mubeat.cms.dto.Result;
import kr.mubeat.cms.dto.recommend.home.HomeDTO;
import kr.mubeat.cms.enumerations.EnumResCode;
import kr.mubeat.cms.repository.recommend.home.HomeRepository;
import kr.mubeat.cms.service.recommend.home.HomeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by moonkyu.lee on 2017. 11. 3..
 */
@Service
public class HomeServiceImpl implements HomeService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private HomeRepository homeRepository;

    @Autowired
    public HomeServiceImpl(
            HomeRepository homeRepository
    ) {
        this.homeRepository = homeRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<HomeDTO> getHomes() {
        return homeRepository.getHomes();
    }

    @Override
    @Transactional(readOnly = false)
    public Result saveHome(List<HomeDTO> homeDTOList) {

        homeRepository.deleteAllInBatch();

        for (HomeDTO homeDTO : homeDTOList) {
            if (homeDTO.getRecommendHomeId() < 1
                ||
                homeDTO.getRecommendHomeId() > 5
                ) {
                return new Result(EnumResCode.E504);
            }
            homeRepository.homeSave(homeDTO.getRecommendHomeId(), homeDTO.getClipMetaId());
        }
        return new Result(EnumResCode.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomPageResult getSearchClipList(CustomSearchParam searchParam) {
        CustomPageResult pageResult = new CustomPageResult(searchParam);
        pageResult.setPageResult(homeRepository.getSearchClipList(searchParam));
        return pageResult;
    }

}
