package kr.mubeat.cms.controller.recommend;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.mubeat.cms.dto.CustomPageResult;
import kr.mubeat.cms.dto.CustomSearchParam;
import kr.mubeat.cms.dto.Result;
import kr.mubeat.cms.dto.recommend.theme.ThemeDTO;
import kr.mubeat.cms.dto.recommend.theme.ThemePosition;
import kr.mubeat.cms.enumerations.EnumResCode;
import kr.mubeat.cms.service.recommend.theme.ThemeItemService;
import kr.mubeat.cms.service.recommend.theme.ThemeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;
import java.io.IOException;
import java.util.List;

/**
 * Created by moonkyu.lee on 2017. 10. 31..
 */
@Controller
@RequestMapping(value = "/recommend")
@RolesAllowed("MENU_MEMBER")
public class ThemeController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private ThemeService themeService;
    private ThemeItemService themeItemService;

    @Autowired
    public ThemeController(
        ThemeService themeService,
        ThemeItemService themeItemService
    ) {
        this.themeService = themeService;
        this.themeItemService = themeItemService;
    }

    /**
     * 테마 추천 리스트 요청
     * @param model
     * @return
     */
    @RequestMapping(value = "/theme", method = RequestMethod.GET)
    public String themeView(
        Model model
    ) {
        model.addAttribute("result", themeService.getThemes());
        return "/recommend/theme_list";
    }

    /**
     * 테마 추천 등록
     * @param img
     * @param body
     * @return
     */
    @RequestMapping(value = "/theme", method = RequestMethod.POST)
    public @ResponseBody
    Result addTheme(
        @RequestPart(name = "img", required = false) MultipartFile img,
        @RequestPart("body") String body
    ) {
        ThemeDTO param = null;

        try {
              param = new ObjectMapper().readValue(body, ThemeDTO.class);
        } catch (IOException e) {
            return new Result(EnumResCode.E999);
        }

        return themeService.saveTheme(param, img);
    }

    /**
     * 테마 추천 상세 보기
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/theme/detail/{id}", method = RequestMethod.GET)
    public String themeDetail(
        @PathVariable Integer id,
        Model model
    ) {
        model.addAttribute("result", themeService.getTheme(id));
        return "/recommend/theme_detail";
    }

    /**
     * 테마 추천 상세 업데이트
     * @param id
     * @param img
     * @param body
     * @return
     */
    @RequestMapping(value = "/theme/{id}", method = RequestMethod.PUT)
    public @ResponseBody
    Result updateTheme(
        @PathVariable Integer id,
        @RequestPart(name = "img", required = false) MultipartFile img,
        @RequestPart("body") String body
    ) {
        ThemeDTO param = null;

        try {
            param = new ObjectMapper().readValue(body, ThemeDTO.class);

            if (param.getRecommendThemeId() != id) {
                return new Result(EnumResCode.E504);
            }
        } catch (IOException e) {
            return new Result(EnumResCode.E999);
        }

        return themeService.saveTheme(param, img);
    }

    /**
     * 테마 추천 아이템 삭제
     * @param id
     * @return
     */
    @RequestMapping(value = "/theme/{id}", method = RequestMethod.DELETE)
    public @ResponseBody
    Result deleteTheme(
        @PathVariable Integer id
    ) {
        return themeService.deleteTheme(id);
    }

    /**
     * 테마 추천 순서 변경
     * @param themePositions
     * @return
     */
    @RequestMapping(value = "/theme/position", method = RequestMethod.PUT)
    public @ResponseBody
    Result updateThemePosition(
        @RequestBody List<ThemePosition> themePositions
    ) {
        return themeService.updatePosition(themePositions);
    }

    /**
     * 테마 추천 아이템 검색
     * @param searchParam
     * @return
     */
    @RequestMapping(value = "/theme/item/search", method = RequestMethod.GET)
    public @ResponseBody
    CustomPageResult themeSearch(
        @ModelAttribute CustomSearchParam searchParam
    ) {
        return themeService.getSearchClipList(searchParam);
    }

    /**
     * 테마 추천 아이템 상세 보기
     * @param searchParam
     * @return
     */
    @RequestMapping(value = "/theme/item", method = RequestMethod.GET)
    public @ResponseBody
    CustomPageResult getThemeItemList(
        @ModelAttribute CustomSearchParam searchParam
    ){
        return themeItemService.getThemeItems(searchParam);
    }

    /**
     * 테마 추천 아이템 상세 클립 추가
     * @param id
     * @param clipMetaId
     * @return
     */
    @RequestMapping(value = "/theme/item/{id}/{clipMetaId}", method = RequestMethod.POST)
    public @ResponseBody
    Result addThemeItem(
        @PathVariable Integer id,
        @PathVariable Long clipMetaId
    ) {
        return themeItemService.addThemeItem(id, clipMetaId);
    }

    /**
     * 테마 추천 아이템 상세 클립 삭제
     * @param id
     * @param clipMetaId
     * @return
     */
    @RequestMapping(value = "/theme/item/{id}/{clipMetaId}", method = RequestMethod.DELETE)
    public @ResponseBody
    Result deleteThemeItem(
        @PathVariable Integer id,
        @PathVariable Long clipMetaId
    ) {
        return themeItemService.deleteThemeItem(id, clipMetaId);
    }
}
