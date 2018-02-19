package kr.mubeat.cms.controller.recommend;

import kr.mubeat.cms.dto.CustomPageResult;
import kr.mubeat.cms.dto.CustomSearchParam;
import kr.mubeat.cms.dto.Result;
import kr.mubeat.cms.dto.recommend.home.HomeDTO;
import kr.mubeat.cms.service.recommend.home.HomeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

/**
 * Created by moonkyu.lee on 2017. 10. 31..
 */
@Controller
@RequestMapping(value = "/recommend")
@RolesAllowed("MENU_MEMBER")
public class HomeController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private HomeService homeService;

    @Autowired
    public HomeController(
            HomeService homeService
    ) {
        this.homeService = homeService;
    }

    /**
     * 메인 추천 시스템 페이지 호출
     * @param model
     * @return
     */
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String homeView(
        Model model
    ) {
        model.addAttribute("result", homeService.getHomes());
        return "/recommend/home";
    }

    /**
     * 메인 추천 시스템 업데이트
     * @param homeDTOList
     * @return
     */
    @RequestMapping(value = "/home", method = RequestMethod.PUT)
    public @ResponseBody
    Result homeUpdate(
        @RequestBody List<HomeDTO> homeDTOList
    ) {
        return homeService.saveHome(homeDTOList);
    }

    /**
     * 메인 추천 검색
     * @param searchParam
     * @return
     */
    @RequestMapping(value = "/home/search", method = RequestMethod.GET)
    public @ResponseBody
    CustomPageResult homeSearch(
            @ModelAttribute CustomSearchParam searchParam
    ) {
        return homeService.getSearchClipList(searchParam);
    }
}
