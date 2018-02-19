package kr.mubeat.cms.controller.manager;

import kr.mubeat.cms.annotation.admin.SuperAdmin;
import kr.mubeat.cms.dto.CustomSearchParam;
import kr.mubeat.cms.dto.Result;
import kr.mubeat.cms.dto.manager.user.UserDTO;
import kr.mubeat.cms.service.manager.user.UserService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

/**
 * Created by moonkyu.lee on 2017. 8. 21..
 */
@SuperAdmin
@Controller
@RequestMapping(value = "/manager")
@RolesAllowed("MENU_MEMBER")
public class MemberController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private UserService userService;

    @Autowired
    public MemberController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 유저 리스트
     * @param model
     * @param searchParam
     * @return
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = UserDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")}
    )
    public String userView(
            Model model,
            @ModelAttribute CustomSearchParam searchParam
    ) {
        model.addAttribute("result", userService.getUserList(searchParam));

        return "manager/user_list";
    }

    /**
     * 상세 유저
     * @param model
     * @param userNo
     * @return
     */
    @RequestMapping(value = "/user/{userNo}", method = RequestMethod.GET)
    public String userDetailView(
            Model model,
            @PathVariable Long userNo
    ) {
        model.addAttribute("detailResult", userService.getUserDetail(userNo));

        return "manager/user_detail";
    }

    /**
     * 유저 상태값 변경
     * @param userNo
     * @param stateCode 탈퇴 Delete, 회원 Yes, 블럭 Block
     * @return
     */
    @RequestMapping(value = "/user/{userNo}", method = RequestMethod.PUT)
    public @ResponseBody
    Result userStateUpdate(
            @PathVariable Long userNo,
            @RequestBody String stateCode
    ) {
        return userService.updateUserState(userNo, stateCode);
    }

}
