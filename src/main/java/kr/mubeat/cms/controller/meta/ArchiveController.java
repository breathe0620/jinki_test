package kr.mubeat.cms.controller.meta;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.mubeat.cms.dto.Result;
import kr.mubeat.cms.dto.meta.archive.ArchiveMBC;
import kr.mubeat.cms.dto.meta.archive.ArchiveMeta;
import kr.mubeat.cms.dto.meta.archive.ArchiveS3DTO;
import kr.mubeat.cms.enumerations.EnumResCode;
import kr.mubeat.cms.service.meta.agency.AgencyService;
import kr.mubeat.cms.service.meta.archive.ArchiveService;
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
 * Created by moonkyu.lee on 2017. 9. 19..
 */
@Controller
@RolesAllowed("MENU_META")
@RequestMapping("/meta")
public class ArchiveController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private ArchiveService archiveService;
    private AgencyService agencyService;

    @Autowired
    public ArchiveController(
        ArchiveService archiveService,
        AgencyService agencyService
    ) {
        this.archiveService = archiveService;
        this.agencyService = agencyService;
    }

    /**
     * FTP로 받은 데이터 확인
     * @param model
     * @return
     */
    @RequestMapping(value = "/archive", method = RequestMethod.GET)
    public String archiveView(
        Model model
    ){
        model.addAttribute("list", archiveService.getList("/mbc"));
        model.addAttribute("corporators", archiveService.getCorporators());
        model.addAttribute("agency", agencyService.getAllAgency());
        return "meta/archive";
    }

    /**
     * 회사별 보기 (준비중.. 사용 금지)
     * @param corporator
     * @return
     */
    @RequestMapping(value = "/archive/{corporator}", method = RequestMethod.GET)
    public @ResponseBody
    List<ArchiveS3DTO> archiveList(
        @PathVariable String corporator
    ) {
        return archiveService.getList(corporator);
    }

    /**
     * 수집용 정보 가져오기
     * @param xml
     * @param video
     * @return
     */
    @RequestMapping(value = "/archive/edit", method = RequestMethod.GET)
    public @ResponseBody
    ArchiveMBC archiveXML(
        @RequestParam String xml,
        @RequestParam String video
    ) {
        return archiveService.parseXML(xml, video);
    }


    /**
     * 프로그램 정보 가져오기
     * @return
     */
    @RequestMapping(value = "/archive/programs/{corporator}", method = RequestMethod.GET)
    public @ResponseBody
    Result archivePrograms(
        @PathVariable String corporator
    ){
        Result result = new Result(EnumResCode.OK);
        result.setData(archiveService.getPrograms(corporator));

        return result;
    }

    /**
     * 영상 확인
     * @param model
     * @param video
     * @return
     */
    @RequestMapping(value = "/archive/video", method = RequestMethod.GET)
    public String archiveVideo(
        Model model,
        @RequestParam String video
    ) {
        model.addAttribute("url", "https://s3.ap-northeast-2.amazonaws.com/mubeats3ftp/"+video);
        return "meta/archive_origin_video";
    }

    /**
     * 아카이브 영상 등록 페이지 호출
     * @param model
     * @return
     */
    @RequestMapping(value = "/archive/regist", method = RequestMethod.GET)
    public String newClipRegist(
        Model model
    ) {
        return "meta/archive_clip_regist";
    }

    /**
     * 클립 등록
     * @param video
     * @param thumbImg
     * @param body
     * @return
     */
    @RequestMapping(value = "/archive/regist", method = RequestMethod.PUT)
    public @ResponseBody
    Result clipRegist(
        @RequestPart(name = "video", required = false) MultipartFile video,
        @RequestPart(name = "thumbImg", required = false) MultipartFile thumbImg,
        @RequestPart("body") String body
    ) {
        ArchiveMeta archiveMeta = null;
        try {
            archiveMeta = new ObjectMapper().readValue(body, ArchiveMeta.class);
        } catch (IOException e) {
            return new Result(EnumResCode.E999);
        }

        return archiveService.clipRegist(archiveMeta, video, thumbImg);
    }
}
