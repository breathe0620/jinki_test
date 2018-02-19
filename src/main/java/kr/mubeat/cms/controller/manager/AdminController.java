package kr.mubeat.cms.controller.manager;

import kr.mubeat.cms.annotation.admin.SuperAdmin;
import kr.mubeat.cms.config.Constants;
import kr.mubeat.cms.enumerations.EnumResCode;
import kr.mubeat.cms.domain.manager.admin.Admin;
import kr.mubeat.cms.domain.manager.admin.AuthToken;
import kr.mubeat.cms.dto.Result;
import kr.mubeat.cms.service.common.CommonService;
import kr.mubeat.cms.service.manager.admin.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpSession;

/**
 * Created by doohwan.yoo on 2017. 5. 26..
 */
@Controller
@RequestMapping(value = "/manager")
@RolesAllowed("MENU_MEMBER")
public class AdminController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private AdminService memberMgmtService;
    private CommonService commonService;

    @Autowired
    public AdminController(
            AdminService memberMgmtService,
            CommonService commonService
    ) {
        this.memberMgmtService = memberMgmtService;
        this.commonService = commonService;
    }

    /**
     * 관리자 리스트
     * @param model
     * @param pageable
     * @return
     */
    @SuperAdmin
    @RequestMapping(value="/admin", method = RequestMethod.GET)
    public String adminListView(
            Model model,
            @PageableDefault(size = 15) Pageable pageable
    ) {
        model.addAttribute("pageResult", memberMgmtService.getAdminList(pageable));
        return "manager/admin_list";
    }

    /**
     * 관리자 생성
     * @return
     */
    @RequestMapping(value="/admin/create", method = RequestMethod.GET)
    public String adminCreateView() {
        return "manager/admin_create";
    }

    /**
     * 관리자 가입
     * @param input
     * @return
     */
    @RequestMapping(value="/admin/signup", method = RequestMethod.POST)
    public @ResponseBody
    Result adminSignUp(@RequestBody Admin input) {
        return commonService.signupAdmin(input);
    }

    /**
     * 관리자 상세 보기
     * @param model
     * @param adminNo
     * @return
     */
    @SuperAdmin
    @RequestMapping(value="/admin/{adminNo}", method = RequestMethod.GET)
    public String adminDetailView(
            Model model,
            @PathVariable Long adminNo
    ) {
        model.addAttribute("detailResult", memberMgmtService.getAdminDetail(adminNo));

        return "manager/admin_detail";
    }

    /**
     * 관리자 업데이트
     * @param adminNo
     * @param input
     * @return
     */
    @SuperAdmin
    @RequestMapping(value="/admin/{adminNo}", method = RequestMethod.PUT)
    public @ResponseBody
    Result adminSignUp(
            @PathVariable Long adminNo,
            @RequestBody Admin input
    ) {
        return memberMgmtService.updateAdminInfo(adminNo, input);
    }

    /**
     * 자신 상테 보기
     * @param session
     * @param model
     * @return
     */
    @RolesAllowed("MENU_META")
    @RequestMapping(value = "/admin/profile", method = RequestMethod.GET)
    public String adminProfileView(HttpSession session, Model model) {

        AuthToken authToken = (AuthToken)session.getAttribute(Constants.SESSION_IDENTIFIER);

        model.addAttribute("profile", memberMgmtService.getAdminDetail(authToken.getAdminNo()));

        return "manager/admin_profile";
    }

    /**
     * 자신 상태 업데이트
     * @param session
     * @param input
     * @return
     */
    @RolesAllowed("MENU_META")
    @RequestMapping(value = "/admin/profile", method = RequestMethod.PUT)
    public @ResponseBody
    Result adminProfileUpdate(HttpSession session, @RequestBody Admin input) {

        AuthToken authToken = (AuthToken)session.getAttribute(Constants.SESSION_IDENTIFIER);

        if (authToken.getAdminNo() == input.getAdminNo()) {
            return memberMgmtService.updateAdminInfo(input.getAdminNo(), input);
        }

        return new Result(EnumResCode.E006);
    }
}
