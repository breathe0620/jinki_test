package kr.mubeat.cms.repository.recommend.theme;

import kr.mubeat.cms.dto.CustomSearchParam;
import kr.mubeat.cms.dto.recommend.theme.ClipDTO;
import org.springframework.data.domain.Page;

/**
 * Created by moonkyu.lee on 2017. 11. 6..
 */
public interface ThemeItemRepositoryCustom {

    Page<ClipDTO> getThemeItems(CustomSearchParam searchParam);

    Long deleteByThemeId(Integer recommendThemeId);

}
