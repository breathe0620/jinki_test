package kr.mubeat.cms.service.recommend.theme;

import kr.mubeat.cms.domain.recommend.theme.Theme;
import kr.mubeat.cms.dto.CustomPageResult;
import kr.mubeat.cms.dto.CustomSearchParam;
import kr.mubeat.cms.dto.Result;
import kr.mubeat.cms.dto.recommend.theme.ThemeDTO;
import kr.mubeat.cms.dto.recommend.theme.ThemePosition;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by moonkyu.lee on 2017. 10. 31..
 */
public interface ThemeService {

    List<ThemeDTO> getThemes();
    Theme getTheme(Integer recommendThemeId);
    Result saveTheme(ThemeDTO themeDTO, MultipartFile img);
    Result deleteTheme(Integer recommendThemeId);

    CustomPageResult getSearchClipList(CustomSearchParam searchParam);
    Result updatePosition(List<ThemePosition> themePositions);

}
