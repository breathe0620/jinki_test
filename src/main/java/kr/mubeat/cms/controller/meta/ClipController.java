package kr.mubeat.cms.controller.meta;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.mubeat.cms.annotation.admin.SuperAdmin;
import kr.mubeat.cms.dto.SignedCookie;
import kr.mubeat.cms.dto.meta.clip.TranscodeDTO;
import kr.mubeat.cms.enumerations.AWSServices;
import kr.mubeat.cms.enumerations.EnumResCode;
import kr.mubeat.cms.dto.CustomSearchParam;
import kr.mubeat.cms.dto.Result;
import kr.mubeat.cms.dto.meta.artist.ArtistDTO;
import kr.mubeat.cms.dto.meta.clip.ClipDTO;
import kr.mubeat.cms.dto.meta.song.SongDTO;
import kr.mubeat.cms.service.meta.artist.ArtistService;
import kr.mubeat.cms.service.meta.clip.ClipService;
import kr.mubeat.cms.service.meta.program.ProgramService;
import kr.mubeat.cms.service.meta.song.SongService;
import kr.mubeat.cms.util.AWSV4RequestUtil;
import kr.mubeat.cms.util.SearchWordUtil;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import kr.mubeat.cms.util.SignedCookiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by moonkyu.lee on 2017. 6. 20..
 */
@Controller
@RolesAllowed("MENU_META")
@RequestMapping("/meta")
public class ClipController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${spring.elasticsearch.host}")
    private String host;

    private ClipService clipService;
    private ArtistService artistService;
    private SongService songService;
    private ProgramService programService;

    private AWSV4RequestUtil awsv4RequestUtil;
    private SearchWordUtil searchWordUtil;
    private SignedCookiesUtil signedCookiesUtil;

    @Autowired
    public ClipController(
            ClipService clipService,
            ArtistService artistService,
            SongService songService,
            ProgramService programService,
            AWSV4RequestUtil awsv4RequestUtil,
            SearchWordUtil searchWordUtil,
            SignedCookiesUtil signedCookiesUtil
    ) {
        this.clipService = clipService;
        this.artistService = artistService;
        this.songService = songService;
        this.programService = programService;
        this.awsv4RequestUtil = awsv4RequestUtil;
        this.searchWordUtil = searchWordUtil;
        this.signedCookiesUtil = signedCookiesUtil;
    }

    /**
     * 클립 리스트
     * @param model
     * @param searchParam
     * @return
     */
    @RequestMapping(value = "/clip", method = RequestMethod.GET)
    public String clipView(
            Model model,
            @ModelAttribute CustomSearchParam searchParam
    ) {
        model.addAttribute("result", clipService.getClipList(searchParam));
        model.addAttribute("corporator", programService.getCorporators());
        model.addAttribute("clipDetailCategory", clipService.getClassify());

        return "meta/clip_list";
    }

    /**
     * 클립 데이터 엑셀로 내려받기
     * @param response
     */
    @SuperAdmin
    @RequestMapping(value = "/clip/excel", method = RequestMethod.GET)
    public void clipsForExcel(
            @ModelAttribute CustomSearchParam searchParam,
            HttpServletResponse response
    ) {
        String fileName = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(new Date());

        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"Clips_" + fileName + ".xlsx" + "\";");
        response.setHeader("Content-Transfer-Encoding", "binary;");

        try {
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(clipService.getClipsForExcel(searchParam));
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {

        }
    }

//    /**
//     * 클립 데이터 엑셀로 배치 업데이트
//     * @param excel
//     * @return
//     */
//    @SuperAdmin
//    @RequestMapping(value = "/clip/excel", method = RequestMethod.PUT)
//    public @ResponseBody
//    Result clipMetaUpdateByExcel(
//            @RequestPart(name = "excel", required = true)MultipartFile excel
//    ) {
//        return clipService.xlsxToUpdateClipMeta(excel);
//    }

    /**
     * 클립 데이터 엑셀로 배치 업데이트
     * @param excel
     * @return
     */
    @SuperAdmin
    @RequestMapping(value = "/clip/excel", method = RequestMethod.PUT)
    public @ResponseBody
    Result clipMetaUpdateByExcel(
            @RequestPart(name = "excel", required = true)MultipartFile excel
    ) {
        return clipService.xlsxToUpdateClipMeta(excel);
//        Result result = new Result(EnumResCode.OK);
//        result.setData(clipService.xlsxToUpdateClipMeta(excel));
//
//        return result;
    }

    /**
     * 클립 수정
     * @param id
     * @param body
     * @return
     */
    @RequestMapping(value = "/clip/{id}", method = RequestMethod.PUT)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = ClipDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")}
    )
    public @ResponseBody
    Result clipUpdate(
            @PathVariable Long id,
            @RequestPart("body") String body
    ) {
        logger.info("in check : " + body);

        Result result = null;

        try {
            ClipDTO param = new ObjectMapper().readValue(body, ClipDTO.class);
            result = clipService.updateClipInfo(id, param);
        } catch (Exception e) {
            return new Result(EnumResCode.E999);
        }

        return result;
    }

    /**
     * 클립 디테일 정보 가져오기
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/clip/detail/{id}", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = ClipDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")}
    )
    public String clipDetailView(Model model, @PathVariable Long id) {
        ClipDTO result = clipService.getClipDetail(id);

        model.addAttribute("clipDetailCategory", clipService.getClassify());
        model.addAttribute("songGenre", songService.getSongGenre());
        model.addAttribute("detailResult", result);

        return "meta/clip_detail";
    }

    /**
     * 사인드 쿠키 로 클립 영상 불러오기
     * @param model
     * @param id
     * @param bitrate
     * @param response
     * @return
     */
    @RequestMapping(value = "/clip/video/{id}/{bitrate}", method = RequestMethod.GET)
    public String clipVideo(
            Model model,
            @PathVariable Long id,
            @PathVariable String bitrate,
            HttpServletResponse response
    ) {
        SignedCookie signedCookie = signedCookiesUtil.getSignedCookies(id);

        if (signedCookie == null) {
            return "meta/clip_no_video";
        }

        Cookie policy = new Cookie("CloudFront-Policy",signedCookie.getPolicy());
        policy.setDomain("cms.mubeat.tv");
        policy.setPath("/");
        policy.setHttpOnly(true);
        policy.setMaxAge(3600);

        Cookie signature = new Cookie("CloudFront-Signature",signedCookie.getSignature());
        signature.setDomain("cms.mubeat.tv");
        signature.setPath("/");
        signature.setHttpOnly(true);
        signature.setMaxAge(3600);

        Cookie keyPairId = new Cookie("CloudFront-Key-Pair-Id",signedCookie.getKeyPairId());
        keyPairId.setDomain("cms.mubeat.tv");
        keyPairId.setHttpOnly(true);
        keyPairId.setPath("/");
        keyPairId.setMaxAge(3600);

        response.addCookie(policy);
        response.addCookie(signature);
        response.addCookie(keyPairId);

        model.addAttribute("id", id);
        model.addAttribute("bitrate", bitrate);

        return "meta/clip_video";
    }

    /**
     * 아티스트 정보 가져오기
     * @param id
     * @return
     */
    @RequestMapping(value = "/clip/artist/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Result clipArtist(
            @PathVariable Long id
    ) {
        Result result = new Result(EnumResCode.OK);
        result.setData(artistService.getArtistDetail(id));

        return result;
    }

    /**
     * 아티스트 정보 수정 폼
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/clip/artist/editor/{id}", method = RequestMethod.GET)
    public String clipArtistEditor(
            Model model,
            @PathVariable Long id
    ) {
        model.addAttribute("result", artistService.getArtistDetail(id));

        return "meta/clip_artist_editor";
    }

    /**
     * 곡 정보 가져오기
     * @param id
     * @return
     */
    @RequestMapping(value = "/clip/song/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Result clipSong(
            @PathVariable Long id
    ) {
        Result result = new Result(EnumResCode.OK);
        result.setData(songService.getSongDetail(id));

        return result;
    }

    /**
     * 곡 정보 수정 폼
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/clip/song/editor/{id}", method = RequestMethod.GET)
    public String clipSongEditor(
            Model model,
            @PathVariable Long id
    ) {
        model.addAttribute("result", songService.getSongDetail(id));

        return "meta/clip_song_editor";
    }

    @RequestMapping(value = "/clip/transcodedStates", method = RequestMethod.GET)
    public @ResponseBody
    TranscodeDTO clipTranscodedStates() {
        return clipService.getTranscodeStates();
    }

    /**
     * 클립 서치
     * 테스트용
     */

    /**
     * 클립 서치용 데이터 확인
     * @return
     */
    @SuperAdmin
    @RequestMapping(value = "/clip/search", method = RequestMethod.GET)
    public String clipSearchForm() {
        return "meta/clip_search";
    }

    /**
     * 이런 형태로 변경

     GET /meta/_search
     {
     "query": {
     "query_string": {
     "default_field": "keyword",
     "query": "*피* OR *땀* OR *눈*"
     }
     }
     }

     * @param keyword
     * @return
     */
    @RequestMapping(value = "/clip/search/{keyword}", method = RequestMethod.GET)
    public @ResponseBody
    String clipSearch(
            @PathVariable String keyword
    ) {
        String payload = "{\"query\":{\"query_string\":{\"default_field\":\"keyword\",\"query\":\""+searchWordUtil.convertKeyword(keyword)+"\"}}}";

        return awsv4RequestUtil.request(
                RequestMethod.POST,
                AWSServices.es,
                host,
                "/meta/_search",
                payload
        );
    }

    /**
     * 클립 전체 데이터 ElasticSearch 에 등록
     */
    @SuperAdmin
    @RequestMapping(value = "/clip-es-all", method = RequestMethod.GET)
    public @ResponseBody
    Result clipSearchData() {
        return clipService.getAndPutAllClipForElasticSearch();
    }

    /**
     * 원본 영상 보기
     * @return
     */
    @SuperAdmin
    @RequestMapping(value = "/clip/origin/video", method = RequestMethod.GET)
    public String clipVideo(
        Model model,
        @RequestParam String url
    ) {
        model.addAttribute("url", url);
        return "meta/clip_origin_video";
    }

}
