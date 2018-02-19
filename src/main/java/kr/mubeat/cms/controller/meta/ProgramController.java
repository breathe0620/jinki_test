package kr.mubeat.cms.controller.meta;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.mubeat.cms.annotation.admin.SuperAdmin;
import kr.mubeat.cms.dto.CustomSearchParam;
import kr.mubeat.cms.dto.Result;
import kr.mubeat.cms.dto.meta.program.ProgramMetaDTO;
import kr.mubeat.cms.enumerations.EnumResCode;
import kr.mubeat.cms.service.meta.program.ProgramService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
 * Created by moonkyu.lee on 2017. 9. 22..
 */
@Controller
@RolesAllowed("MENU_META")
@RequestMapping("/meta")
public class ProgramController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private ProgramService programService;

    @Autowired
    public ProgramController(
        ProgramService programService
    ) {
        this.programService = programService;
    }

    /**
     * 프로그램 리스트
     * @param model
     * @param searchParam
     * @return
     */
    @RequestMapping(value = "/program", method = RequestMethod.GET)
    public String programView(
        Model model,
        @ModelAttribute CustomSearchParam searchParam
    ) {
        model.addAttribute("result", programService.getProgramList(searchParam));
        model.addAttribute("corporator", programService.getCorporators());

        return "meta/program_list";
    }

    /**
     * 프로그램 수정
     * @param id
     * @param body
     * @return
     */
    @RequestMapping(value = "/program/{id}", method = RequestMethod.PUT)
    public @ResponseBody
    Result updateProgram(
        @PathVariable String id,
        @RequestPart("body") String body
    ) {
        Result result = null;

        try {
            ProgramMetaDTO param = new ObjectMapper().readValue(body, ProgramMetaDTO.class);
            result = programService.updateProgram(id, param);
        } catch (Exception e) {
            return new Result(EnumResCode.E999);
        }

        return result;
    }

    /**
     * 프로그램 엑셀로 받아 보기
     * @param response
     */
    @SuperAdmin
    @RequestMapping(value = "/program/excel", method = RequestMethod.GET)
    public void clipProgramForExcel(
        HttpServletResponse response
    ) {
        String fileName = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(new Date());

        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"Program_" + fileName + ".xlsx" + "\";");
        response.setHeader("Content-Transfer-Encoding", "binary;");

        try {
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(programService.getProgramForExcel());
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {

        }
    }

    /**
     * 엑셀로 프로그램 메타 업데이트
     * @param excel
     * @return
     */
    @SuperAdmin
    @RequestMapping(value = "/program/excel", method = RequestMethod.PUT)
    public @ResponseBody
    Result programMetaUpdateByExcel(
            @RequestPart(name = "excel", required = true)MultipartFile excel
    ) {
        return programService.xlsxToUpdateProgramMeta(excel);
    }

    /**
     * 프로그램 정보 가져오기
     * @param corporator
     * @return
     */
    @RequestMapping(value = "/programs/{corporator}", method = RequestMethod.GET)
    public @ResponseBody
    Result programs(
        @PathVariable String corporator
    ) {
        Result result = new Result(EnumResCode.OK);
        result.setData(programService.getPrograms(corporator));

        return result;
    }

}
