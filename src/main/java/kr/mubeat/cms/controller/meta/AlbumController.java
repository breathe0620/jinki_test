package kr.mubeat.cms.controller.meta;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.mubeat.cms.annotation.admin.SuperAdmin;
import kr.mubeat.cms.enumerations.EnumResCode;
import kr.mubeat.cms.domain.meta.album.AlbumType;
import kr.mubeat.cms.dto.CustomSearchParam;
import kr.mubeat.cms.dto.meta.artist.ArtistDTO;
import kr.mubeat.cms.dto.Result;
import kr.mubeat.cms.dto.meta.album.AlbumDTO;
import kr.mubeat.cms.service.meta.album.AlbumService;
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
 * Created by moonkyu.lee on 2017. 6. 9..
 */
@Controller
@RolesAllowed("MENU_META")
@RequestMapping("/meta")
public class AlbumController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private AlbumService albumService;

    @Autowired
    public AlbumController(AlbumService albumService) { this.albumService = albumService; }

    /**
     * 앨범 리스트
     * @param model
     * @param searchParam
     * @return
     */
    @RequestMapping(value = "/album", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = AlbumDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")}
    )
    public String albumView(
        Model model,
        @ModelAttribute CustomSearchParam searchParam) {

        model.addAttribute("result", albumService.getAlbumList(searchParam));

        return "meta/album_list";

    }

    /**
     * 앨범 생성
     * @param albumImg
     * @param body
     * @return Result.class
     */
    @RequestMapping(value = "/album", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = AlbumDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")}
    )
    public @ResponseBody
    Result albumCreate(
            @RequestPart(name = "albumImg", required = false) MultipartFile albumImg,
            @RequestPart("body") String body) {

        AlbumDTO param = null;

        try {
            param = new ObjectMapper().readValue(body, AlbumDTO.class);
        }
        catch (IOException e) {
            return new Result(EnumResCode.E999);
        }

        return albumService.createAlbumInfo(albumImg, param);
    }

    /**
     * 앨범 생성 폼
     * @param model
     * @return
     */
    @RequestMapping(value = "/album/create", method = RequestMethod.GET)
    String albumCreateView(Model model) {
        model.addAttribute("albumTypeList", albumService.getAlbumType());
        return "meta/album_create";
    }

    /**
     * 앨범 수정
     * @param id
     * @param albumImg
     * @param body
     * @return
     */
    @RequestMapping(value = "/album/{id}", method = RequestMethod.PUT)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = ArtistDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")}
    )
    public @ResponseBody
    Result albumUpdate(
            @PathVariable Long id,
            @RequestPart(name = "albumImg", required = false) MultipartFile albumImg,
            @RequestPart("body") String body
    ) {
        Result result = null;

        try {
            AlbumDTO param = new ObjectMapper().readValue(body, AlbumDTO.class);
            result = albumService.updateAlbumInfo(
                    albumImg,
                    id,
                    param
            );
        }
        catch (Exception e) {
            return new Result(EnumResCode.E999);
        }

        return result;
    }

    /**
     * 앨범 정보 상세 보기
     * @param model
     * @param id
     * @return AlbumDTO.class
     */
    @RequestMapping(value = "/album/detail/{id}", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = AlbumDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")}
    )
    public String albumDetailView(Model model, @PathVariable Long id) {

        AlbumDTO result = albumService.getAlbumDetail(id);

        model.addAttribute("detailResult", result);
        model.addAttribute("albumType", albumService.getAlbumType());

        return "meta/album_detail";
    }

    /**
     * 앨범 삭제
     */
    @RequestMapping(value = "/album/{id}", method = RequestMethod.DELETE)
    public @ResponseBody
    Result albumDelete(@PathVariable Long id) {
        logger.info("앨범 삭제 요청 id : " + id);
        return albumService.deleteAlbumInfo(id);
    }

    @RequestMapping(value = "/search-album-meta/{prefix}", method = RequestMethod.GET)
    public @ResponseBody
    Result searchAlbumMeta(@PathVariable String prefix) { return albumService.getAlbumMetaFromEs(prefix); }

    /**
     * 앨범 타입 추가
     */
    @RequestMapping(value = "/album-type", method = RequestMethod.POST)
    public @ResponseBody
    Result albumTypeIdCreate(@RequestBody AlbumType param) { return albumService.createAlbumType(param); }

    /**
     * 앨범 전체 데이터 ElasticSearch 에 등록
     * @return
     */
    @SuperAdmin
    @RequestMapping(value = "/album-es-all", method = RequestMethod.GET)
    public @ResponseBody
    Result albumElasticSearchAddAll() {
        return albumService.getAndPutAllAlbumForElasticSearch();
    }

    /**
     * 앨범 MID 중복 확인
     */
    @RequestMapping(value = "/album/check/{id}", method = RequestMethod.GET)
    public @ResponseBody
    boolean albumOriginIdCheck(@PathVariable Long id) {
        return albumService.getCheckAlbumOriginId(id);
    }

    /**
     * 앨범 데이터 엑셀로 내려받기
     */
    @SuperAdmin
    @RequestMapping(value = "/album/excel", method = RequestMethod.GET)
    public void albumForExcel(
            HttpServletResponse response
    ) {
        String fileName = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(new Date());

        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"Album_" + fileName + ".xlsx" + "\";");
        response.setHeader("Content-Transfer-Encoding", "binary;");

        try {
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(albumService.getAlbumsForExcel());
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {}
    }

    /**
     * 엑셀로 앨범 메타 업데이트
     * @param excel
     * @return
     */
    @SuperAdmin
    @RequestMapping(value = "/album/excel", method = RequestMethod.PUT)
    public @ResponseBody
    Result albumMetaUpdateByExcel(
            @RequestPart(name = "excel", required = true)MultipartFile excel
    ) {
        return albumService.xlsxToUpdateAlbumMeta(excel);
    }

}
