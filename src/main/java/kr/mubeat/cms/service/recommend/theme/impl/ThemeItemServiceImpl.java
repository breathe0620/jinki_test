package kr.mubeat.cms.service.recommend.theme.impl;

import kr.mubeat.cms.domain.recommend.theme.ThemeItem;
import kr.mubeat.cms.dto.CustomPageResult;
import kr.mubeat.cms.dto.CustomSearchParam;
import kr.mubeat.cms.dto.Result;
import kr.mubeat.cms.enumerations.EnumResCode;
import kr.mubeat.cms.repository.recommend.theme.ThemeItemRepository;
import kr.mubeat.cms.service.recommend.theme.ThemeItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by moonkyu.lee on 2017. 11. 7..
 */
@Service
public class ThemeItemServiceImpl implements ThemeItemService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private ThemeItemRepository themeItemRepository;

    @Autowired
    public ThemeItemServiceImpl(
        ThemeItemRepository themeItemRepository
    ) {
        this.themeItemRepository = themeItemRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public CustomPageResult getThemeItems(CustomSearchParam searchParam) {
        CustomPageResult pageResult = new CustomPageResult(searchParam);
        pageResult.setPageResult(themeItemRepository.getThemeItems(searchParam));
        return pageResult;
    }

    @Override
    @Transactional(readOnly = false)
    public Result addThemeItem(Integer recommendThemeId, Long clipMetaId) {
        themeItemRepository.save(new ThemeItem(recommendThemeId, clipMetaId));
        return new Result(EnumResCode.OK);
    }

    @Override
    @Transactional(readOnly = false)
    public Result deleteThemeItem(Integer recommendThemeId, Long clipMetaId) {
        themeItemRepository.delete(new ThemeItem(recommendThemeId, clipMetaId));
        return new Result(EnumResCode.OK);
    }
}
