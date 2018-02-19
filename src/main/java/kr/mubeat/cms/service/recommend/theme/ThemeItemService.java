package kr.mubeat.cms.service.recommend.theme;

import kr.mubeat.cms.dto.CustomPageResult;
import kr.mubeat.cms.dto.CustomSearchParam;
import kr.mubeat.cms.dto.Result;

/**
 * Created by moonkyu.lee on 2017. 11. 7..
 */
public interface ThemeItemService {

    CustomPageResult getThemeItems(CustomSearchParam searchParam);
    Result addThemeItem(Integer recommendThemeId, Long clipMetaId);
    Result deleteThemeItem(Integer recommendThemeId, Long clipMetaId);

}
