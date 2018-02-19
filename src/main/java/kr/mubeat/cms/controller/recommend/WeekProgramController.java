package kr.mubeat.cms.controller.recommend;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.mubeat.cms.dto.Result;
import kr.mubeat.cms.dto.recommend.program.ProgramDTO;
import kr.mubeat.cms.dto.recommend.program.WeekProgramItemDTO;
import kr.mubeat.cms.enumerations.EnumResCode;
import kr.mubeat.cms.service.meta.program.ProgramService;
import kr.mubeat.cms.service.recommend.weekprogram.WeekProgramItemService;
import kr.mubeat.cms.service.recommend.weekprogram.WeekProgramService;
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
public class WeekProgramController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private WeekProgramService weekProgramService;
    private WeekProgramItemService weekProgramItemService;

    private ProgramService programService;

    @Autowired
    public WeekProgramController(
        WeekProgramService weekProgramService,
        WeekProgramItemService weekProgramItemService,
        ProgramService programService
    ) {
        this.weekProgramService = weekProgramService;
        this.weekProgramItemService = weekProgramItemService;
        this.programService = programService;
    }

    /**
     * 1. ProgramList (DB 고정값) + 프로그램 맵핑 변경
     * 2. Program 수정
     * 3. Program 수정용 ProgramList 내려주기
     * 4. Program Item 추가
     * 5. Program Item 삭
     */

    /**
     * 주간 추천 페이지 요청
     * @param model
     * @return
     */
    @RequestMapping(value = "/weekprogram", method = RequestMethod.GET)
    public String weekProgramView(
        Model model
    ) {
        model.addAttribute("result", weekProgramService.getPrograms());
        return "/recommend/week_program";
    }

    /**
     * 주간 추천 상세 아이템 요청
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/weekprogram/detail/{id}", method = RequestMethod.GET)
    public String weekProgramDetail(
        @PathVariable Integer id,
        Model model
    ) {
        model.addAttribute("result", weekProgramService.getProgram(id));
        return "/recommend/week_program_detail";
    }

    /**
     * 주간 추천 상세 아이템 업데이트
     * @param id
     * @param img
     * @param body
     * @return
     */
    @RequestMapping(value = "/weekprogram/{id}", method = RequestMethod.PUT)
    public @ResponseBody
    Result updateWeekProgram(
        @PathVariable Integer id,
        @RequestPart(name = "img", required = false) MultipartFile img,
        @RequestPart("body") String body
    ) {
        ProgramDTO param = null;

        try {
            param = new ObjectMapper().readValue(body, ProgramDTO.class);

            if (param.getRecommendProgramId() != id) {
                return new Result(EnumResCode.E504);
            }
        } catch (IOException e) {
            return new Result(EnumResCode.E999);
        }

        return weekProgramService.updateProgram(param, img);
    }

    /**
     * 방송사 리스트
     * @return
     */
    @RequestMapping(value = "/weekprogram/program/", method = RequestMethod.GET)
    public @ResponseBody
    Result corporator() {
        Result result = new Result(EnumResCode.OK);
        result.setData(programService.getCorporators());
        return result;
    }

    /**
     * 방송사가 방송한 프로그램 리스트
     * @param corporator
     * @return
     */
    @RequestMapping(value = "/weekprogram/program/{corporator}", method = RequestMethod.GET)
    public @ResponseBody
    Result getPrograms(
        @PathVariable String corporator
    ) {
        Result result = new Result(EnumResCode.OK);
        result.setData(programService.getPrograms(corporator));
        return result;
    }

    /**
     * 프로그램의 방영일 리스트
     * @param programId
     * @return
     */
    @RequestMapping(value = "/weekprogram/broaddate/{programId}", method = RequestMethod.GET)
    public @ResponseBody
    Result getProgramBroadDates(
        @PathVariable String programId
    ) {
        return weekProgramItemService.getProgramBroadDates(programId);
    }

    /**
     * 프로그램 방영일의 영상 클립 리스트
     * @param recommendProgramId
     * @param programId
     * @param broadDate
     * @return
     */
    @RequestMapping(value = "/weekprogram/broaddate/{recommendProgramId}/{programId}/{broadDate}", method = RequestMethod.GET)
    public @ResponseBody
    Result getProgramsByBroadDate(
        @PathVariable Integer recommendProgramId,
        @PathVariable String programId,
        @PathVariable String broadDate
    ) {
        return weekProgramItemService.getProgramsByBroadDate(programId, broadDate, recommendProgramId);
    }

    /**
     * 주간 추천 상세 아이템 리스트
     * @param recommendProgramId
     * @return
     */
    @RequestMapping(value = "/weekprogram/item/{recommendProgramId}", method = RequestMethod.GET)
    public @ResponseBody
    Result getProgramItems(
        @PathVariable Integer recommendProgramId
    ) {
        return weekProgramItemService.getProgramitems(recommendProgramId);
    }

    /**
     * 주간 추천 상세 아이템 추가
     * @param weekProgramItems
     * @return
     */
    @RequestMapping(value = "/weekprogram/item", method = RequestMethod.POST)
    public @ResponseBody
    Result addClip(
        @RequestBody List<WeekProgramItemDTO> weekProgramItems
    ) {
        return weekProgramItemService.addClip(weekProgramItems);
    }

    /**
     * 주간 추천 상세 아이템 삭제
     * @param weekProgramItems
     * @return
     */
    @RequestMapping(value = "/weekprogram/item", method = RequestMethod.DELETE)
    public @ResponseBody
    Result deleteClip(
        @RequestBody List<WeekProgramItemDTO> weekProgramItems
    ) {
        return weekProgramItemService.deleteClip(weekProgramItems);
    }

}