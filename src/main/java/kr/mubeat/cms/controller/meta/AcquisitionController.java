package kr.mubeat.cms.controller.meta;

import kr.mubeat.cms.dto.CustomSearchParam;
import kr.mubeat.cms.dto.Result;
import kr.mubeat.cms.dto.meta.acquisition.ProgramSearchDTO;
import kr.mubeat.cms.service.meta.acquisition.AcquisitionService;
import kr.mubeat.cms.service.meta.program.ProgramService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by moonkyu.lee on 2017. 6. 13..
 */
@Controller
@RolesAllowed("MENU_META")
@RequestMapping("/meta")
public class AcquisitionController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private AcquisitionService acquisitionService;
    private ProgramService programService;

    @Autowired
    public AcquisitionController(
            AcquisitionService acquisitionService,
            ProgramService programService
    ) {
        this.acquisitionService = acquisitionService;
        this.programService = programService;
    }

    /**
     * 방송 입수 정보 리스트
     *
     * @param model
     * @param searchParam
     * @return List<AcquisitionDTO>
     */
    @RequestMapping(value = "/acquisition", method = RequestMethod.GET)
    public String acquisitionView(
            Model model,
            @ModelAttribute CustomSearchParam searchParam
    ) {
        model.addAttribute("result", acquisitionService.getAcquisitionList(searchParam));
        model.addAttribute("corporators", programService.getCorporators());
        if (searchParam.getCorporator() != null) {
            model.addAttribute("programs", programService.getPrograms(searchParam.getCorporator()));
        } else {
            model.addAttribute("programs", new ArrayList<ProgramSearchDTO>());
        }

        return "meta/acquisition_list";
    }

    /**
     * 방송 입수 정보 리스트 - 상태별 보기
     *
     * @param model
     * @param searchParam
     * @return
     */
    @RequestMapping(value = "/acquisition/state", method = RequestMethod.GET)
    public String acquisitionStateView(
            Model model,
            @ModelAttribute CustomSearchParam searchParam
    ) {
        model.addAttribute("result", acquisitionService.getAcquisitionStateList(searchParam));

        return "meta/acquisition_state_list";
    }

    /**
     * 방송 입수 정보 상세 보기
     *
     * @param model
     * @param id
     * @return 페이지
     */
    @RequestMapping(value = "/acquisition/detail/{id}", method = RequestMethod.GET)
    public String acquisitionDetailView(Model model, @PathVariable Long id) {

        model.addAttribute("detailResult", acquisitionService.getAcquisitionDetail(id));

        return "meta/acquisition_detail";
    }

    /**
     * 입수 및 트랜스코드 요청
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/acquisition/transcode", method = RequestMethod.PUT)
    public @ResponseBody
    Result acquisitionTranscode(
            @RequestParam List<Long> id
    ) {
        logger.info("in check : " + id);
        return acquisitionService.updateUploadState(id);
    }

    /**
     * 입수 정보 수정
     *
     * @param id
     * @param clipTitle
     * @return
     */
    @RequestMapping(value = "/acquisition/{id}", method = RequestMethod.PUT)
    public @ResponseBody
    Result acquisitionUpdate(
            @PathVariable Long id,
            @RequestBody String clipTitle
    ) {
        return acquisitionService.updateAcquisitionInformation(id, clipTitle);
    }

}
