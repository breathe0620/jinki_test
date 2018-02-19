package kr.mubeat.cms.controller.meta;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.mubeat.cms.annotation.admin.SuperAdmin;
import kr.mubeat.cms.enumerations.EnumResCode;
import kr.mubeat.cms.domain.meta.artist.ArtistType;
import kr.mubeat.cms.dto.CustomSearchParam;
import kr.mubeat.cms.dto.Result;
import kr.mubeat.cms.dto.meta.artist.ArtistDTO;
import kr.mubeat.cms.service.meta.artist.ArtistService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

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
public class ArtistController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private ArtistService artistService;

    @Autowired
    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    /**
     * 아티스트 리스트
     * @param model
     * @param searchParam
     * @return List<ArtistDTO>
     */
    @RequestMapping(value="/artist", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = ArtistDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")}
    )
    public String artistView(
            Model model,
            @ModelAttribute CustomSearchParam searchParam
            ) {
        model.addAttribute("result", artistService.getArtistList(searchParam));

        return "meta/artist_list";
    }

    /**
     * 아티스트 정보 상세 보기
     * @param id
     * @return SongDTO.class
     */
    @RequestMapping(value="/artist/detail/{id}", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = ArtistDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")}
    )
    public
    String artistDetail(Model model, @PathVariable Long id) {

        ArtistDTO result = artistService.getArtistDetail(id);

        model.addAttribute("detailResult", result);
        model.addAttribute("artistTypeList", artistService.getArtistType());

        return  "meta/artist_detail";
    }

    /**
     * 아티스트 생성 폼
     * @param model
     * @return
     */
    @RequestMapping(value="/artist/create", method = RequestMethod.GET)
    String artistCreateView(Model model) {

        model.addAttribute("artistTypeList", artistService.getArtistType());
        return "meta/artist_create";
    }

    /**
     * 아티스트 생성
     * @param body
     * @return Result.class
     */
    @RequestMapping(value="/artist", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = ArtistDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")}
    )
    public @ResponseBody
    Result artistCreate(
            @RequestPart(name = "mainImg", required = false) MultipartFile mainImg,
            @RequestPart(name = "profileImg", required = false) MultipartFile profileImg,
            @RequestPart("body") String body
    ) {
        ArtistDTO param = null;

        try {
            param = new ObjectMapper().readValue(body, ArtistDTO.class);
        }
        catch(IOException e) {
            return new Result(EnumResCode.E999);
        }

        return artistService.createArtistInfo(mainImg, profileImg, param);
    }

    /**
     * 아티스트 삭제
     * @param id
     * @return Result.class
     */
    @RequestMapping(value="/artist/{id}", method = RequestMethod.DELETE)
    public @ResponseBody
    Result artistDelete(@PathVariable Long id) {
        return artistService.deleteArtistInfo(id);
    }

    /**
     * 아티스트 수정
     * @param id
     * @param body
     * @return
     */
    @RequestMapping(value="/artist/{id}", method = RequestMethod.PUT)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = ArtistDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")}
    )
    public @ResponseBody
    Result artistUpdate(
            @PathVariable Long id,
            @RequestPart(name = "mainImg", required = false) MultipartFile mainImg,
            @RequestPart(name = "profileImg", required = false) MultipartFile profileImg,
            @RequestPart("body") String body) {

        logger.info("in check : " + body);

        Result result = null;

        try {
            ArtistDTO param = new ObjectMapper().readValue(body, ArtistDTO.class);
            result = artistService.updateArtistInfo(mainImg, profileImg, id, param);

            logger.info("result : " + result.getCode());
        } catch (Exception e) {
            return new Result(EnumResCode.E999);
        }

        return result;
    }

    /**
     * 아티스트 타입 추가
     * @param param
     * @return
     */
    @RequestMapping(value="/artist-type", method = RequestMethod.POST)
    public @ResponseBody
    Result artistTypeIdCreate(@RequestBody ArtistType param) {
        return artistService.createArtistType(param);
    }

    /**
     * 아티스트 메타 검색
     * @param prefix
     * @return
     */
    @RequestMapping(value="/search-artist-meta/{prefix}", method = RequestMethod.GET)
    public @ResponseBody
    Result searchArtistMeta(@PathVariable String prefix) {
        return artistService.getArtistMetaFromEs(prefix);
    }

    /**
     * 아티스트 유형 생성
     * @return Result.class
     */
    @RequestMapping(value="/artist-type", method = RequestMethod.GET)
    public String artistGenreCreate(Model model) {

        model.addAttribute("artistTypeId", artistService.getArtistType());
        return "meta/artist_type_create";
    }

    /**
     * 아티스트 전체 데이터 ElasticSearch 에 등록
     * @return
     */
    @SuperAdmin
    @RequestMapping(value = "/artist-es-all", method = RequestMethod.GET)
    public @ResponseBody
    Result artistElasticSearchAddAll() {
        return artistService.getAndPutAllArtistForElasticSearch();
    }

    @RequestMapping(value = "/artist-image-crop", method = RequestMethod.GET)
    public String artistImageCrop() {
        return "meta/artist_image_crop";
    }

    /**
     *  곡 MID 중복 확인
     */
    @RequestMapping(value = "/artist/check/{id}", method = RequestMethod.GET)
    public @ResponseBody
    boolean artistOriginIdCheck(@PathVariable Long id) {
        return artistService.getCheckArtistOriginId(id);
    }

    /**
     * 아티스트 데이터 엑셀로 내려받기
     */
    @SuperAdmin
    @RequestMapping(value = "/artist/excel", method = RequestMethod.GET)
    public void artistForExcel(
            HttpServletResponse response
    ) {
        String fileName = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(new Date());

        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"Artists_" + fileName + ".xlsx" + "\";");
        response.setHeader("Content-Transfer-Encoding", "binary;");

        try {
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(artistService.getArtistsForExcel());
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {}
    }

    /**
     * 엑셀로 아티스트 메타 업데이트
     * @param excel
     * @return
     */
    @SuperAdmin
    @RequestMapping(value = "/artist/excel", method = RequestMethod.PUT)
    public @ResponseBody
    Result artistMetaUpdateByExcel(
            @RequestPart(name = "excel", required = true)MultipartFile excel
    ) {
        return artistService.xlsxToUpdateArtistMeta(excel);
    }
}
