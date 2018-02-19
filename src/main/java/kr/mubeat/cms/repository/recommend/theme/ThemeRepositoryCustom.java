package kr.mubeat.cms.repository.recommend.theme;

import kr.mubeat.cms.domain.recommend.theme.Theme;
import kr.mubeat.cms.dto.recommend.theme.ThemeDTO;

import java.util.List;

/**
 * Created by moonkyu.lee on 2017. 11. 6..
 */
public interface ThemeRepositoryCustom {

    List<ThemeDTO> getThemes();
    Long updatePosition(Integer recommendThemeId, Integer position);

}
