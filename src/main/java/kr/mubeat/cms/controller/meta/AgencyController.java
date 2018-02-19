package kr.mubeat.cms.controller.meta;

import kr.mubeat.cms.annotation.admin.SuperAdmin;
import kr.mubeat.cms.domain.meta.agency.Agency;
import kr.mubeat.cms.dto.Result;
import kr.mubeat.cms.dto.meta.agency.AgencyDTO;
import kr.mubeat.cms.service.meta.agency.AgencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by moonkyu.lee on 2017. 6. 13..
 */
@Controller
@RolesAllowed("MENU_META")
@RequestMapping("/meta")
public class AgencyController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private AgencyService agencyService;

    @Autowired
    public AgencyController(AgencyService agencyService) {
        this.agencyService = agencyService;
    }

    /**
     * 소속사 검색
     * @param agencyName
     * @return
     */
    @RequestMapping(value="/agency/{agencyName}", method = RequestMethod.GET)
    public @ResponseBody
    Result findAllAgencyByName(@PathVariable String agencyName) {
        return agencyService.findAllByAgencyName(agencyName);
    }

    /**
     * 소속사 생성
     * @param param
     * @return
     */
    @RequestMapping(value="/agency", method = RequestMethod.POST)
    public @ResponseBody Result addAgency(@RequestBody Agency param) {
        return agencyService.addAgency(param);
    }

    /**
     * 소속사 수정
     * @param agencyId
     * @param param
     * @return
     */
    @RequestMapping(value = "/agency/{agencyId}", method = RequestMethod.PUT)
    public @ResponseBody Result updateAgency(@PathVariable Integer agencyId, @RequestBody AgencyDTO param) {
        return agencyService.updateAgency(agencyId, param);
    }

    /**
     * 소속사 삭제
     * @param agencyId
     * @return
     */
    @RequestMapping(value="/agency/{agencyId}", method = RequestMethod.DELETE)
    public @ResponseBody Result deleteAgency(@PathVariable Integer agencyId) {
        return agencyService.deleteAgency(agencyId);
    }

    /**
     * 소속사 리스트
     * @param model
     * @param pageable
     * @return
     */
    @RequestMapping(value="/agency", method = RequestMethod.GET)
    public @ResponseBody Result agencyView(Model model, @PageableDefault(size = 15) Pageable pageable) {
        return agencyService.getAgency(pageable);
    }

    /**
     * 같은 소속사 정보
     * @param agencyId
     * @return
     */
    @RequestMapping(value="/artist/agency/{agencyId}", method = RequestMethod.GET)
    public @ResponseBody Result sameAgencyArtist(@PathVariable Integer agencyId) {
        return agencyService.getSameAgencyArtist(agencyId);
    }

    /**
     * 소속사 데이터 엑셀로 내려받기
     */
    @SuperAdmin
    @RequestMapping(value = "/agency/excel", method = RequestMethod.GET)
    public void agencyForExcel(
            HttpServletResponse response
    ) {
        String fileName = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(new Date());

        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"Agency_" + fileName + ".xlsx" + "\";");
        response.setHeader("Content-Transfer-Encoding", "binary;");

        try {
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(agencyService.getAgencyForExcel());
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {}
    }

    /**
     * 엑셀로 소속사 메타 업데이트
     * @param excel
     * @return
     */
    @SuperAdmin
    @RequestMapping(value = "/agency/excel", method = RequestMethod.PUT)
    public @ResponseBody
    Result agencyMetaUpdateByExcel(
            @RequestPart(name = "excel", required = true)MultipartFile excel
    ) {
        return agencyService.xlsxToUpdateAgencyMeta(excel);
    }

}
