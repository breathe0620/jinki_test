package kr.mubeat.cms.controller.manager;

import kr.mubeat.cms.annotation.admin.SuperAdmin;
import kr.mubeat.cms.domain.manager.version.Version;
import kr.mubeat.cms.dto.Result;
import kr.mubeat.cms.service.manager.version.VersionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.security.RolesAllowed;

/**
 * Created by moonkyu.lee on 2017. 8. 16..
 */
@Controller
@RequestMapping(value = "/manager")
@RolesAllowed("MENU_MEMBER")
public class ItemController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private VersionService versionService;

    @Autowired
    public ItemController(
            VersionService versionService
    ) {
        this.versionService = versionService;
    }

    /**
     * 앱 버전 페이지 호출
     * @param model
     * @return
     */
    @SuperAdmin
    @RequestMapping(value = "/versions", method = RequestMethod.GET)
    public String versionListView(
            Model model
    ) {
        model.addAttribute("versions", versionService.getVersions());
        return "manager/version_list";
    }

    /**
     * 앱 버전 업데이트
     * @param version
     * @return
     */
    @SuperAdmin
    @RequestMapping(value = "/version", method = RequestMethod.PUT)
    public @ResponseBody
    Result versionUpdate(
            @RequestBody Version version
    ) {
        return versionService.updateVersion(version);
    }

    /**
     * 엑셀 다운로드 페이지 호출
     * @return
     */
    @SuperAdmin
    @RequestMapping(value = "/admin/excels", method = RequestMethod.GET)
    public String excels() {
        return "manager/admin_excels";
    }

    /**
     * ElasticSearch Meta 업데이트 페이지 호출
     * @return
     */
    @SuperAdmin
    @RequestMapping(value = "/admin/es", method = RequestMethod.GET)
    public String es() { return "manager/es"; }

}
