package kr.mubeat.cms.controller.meta;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import kr.mubeat.cms.annotation.admin.SuperAdmin;
import kr.mubeat.cms.domain.meta.song.Song;
import kr.mubeat.cms.domain.meta.song.SongGenre;
import kr.mubeat.cms.dto.CustomSearchParam;
import kr.mubeat.cms.dto.Result;
import kr.mubeat.cms.dto.meta.song.SongDTO;
import kr.mubeat.cms.repository.meta.song.SongOriginRepository;
import kr.mubeat.cms.repository.meta.song.SongRepository;
import kr.mubeat.cms.service.meta.song.SongService;
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
import java.util.List;

/**
 * Created by moonkyu.lee on 2017. 6. 12..
 */
@Controller
@RolesAllowed("MENU_META")
@RequestMapping("/meta")
public class SongController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private SongService songService;

    @Autowired
    public SongController(SongService songService) {
        this.songService = songService;
    }

    /**
     * 곡 리스트
     * @param model
     * @param searchParam
     * @return 곡 페이징, 장르 정보
     */
    @RequestMapping(value="/song", method = RequestMethod.GET)
    public String songView(Model model, @ModelAttribute CustomSearchParam searchParam) {
        model.addAttribute("result", songService.getSongList(searchParam));
        model.addAttribute("genreList", songService.getSongGenre());

        return "meta/song_list";
    }

    /**
     * 곡 정보 상세 보기
     * @param id
     * @return SongDTO.class
     */
    @RequestMapping(value="/song/detail/{id}", method = RequestMethod.GET)
    public String songDetailView(Model model, @PathVariable Long id) {

        SongDTO result = songService.getSongDetail(id);

        // ToDo 장르 리스트를 내려줘서 프론트앤드에서 처리할지 백앤드에서 미리 조인해서 내려줄지 고민해 봐야 함
        model.addAttribute("detailResult", result);
        model.addAttribute("genreList", songService.getSongGenre());

        return  "meta/song_detail";
    }

    /**
     * 곡 정보 생성
     * @param param
     * @return Result.class
     */
    @RequestMapping(value="/song", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SongDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")}
    )
    public @ResponseBody
    Result songCreate(@RequestBody SongDTO param) {
        return songService.createSongInfo(param);
    }

    /**
     * 곡 삭제
     * @param id
     * @return Result.class
     */
    @RequestMapping(value="/song/{id}", method = RequestMethod.DELETE)
    public @ResponseBody
    Result songDelete(@PathVariable Long id) {
        return songService.deleteSongInfo(id);
    }

    /**
     * 곡 생성 폼
     * @param model
     * @return
     */
    @RequestMapping(value="/song/create", method = RequestMethod.GET)
    String songCreateView(Model model) {

        model.addAttribute("genreList", songService.getSongGenre());
        return "meta/song_create";
    }

    /**
     * 곡 정보 업데이트
     * @param id
     * @param param
     * @return SongDTO.class
     */
    @RequestMapping(value="/song/{id}", method = RequestMethod.PUT)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SongDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")}
    )
    public @ResponseBody
    Result songUpdate(@PathVariable Long id, @RequestBody SongDTO param) {
        return songService.updateSongInfo(id, param);
    }

    /**
     * 곡 장르 추가
     * @param param
     * @return
     */
    @RequestMapping(value="/song-genre", method = RequestMethod.POST)
    public @ResponseBody
    Result songGenreCreate(@RequestBody SongGenre param) {
        return songService.createSongGenre(param);
    }

    /**
     * 곡 메타 검색
     * @param prefix
     * @return
     */
    @RequestMapping(value="/search-song-meta/{prefix}", method = RequestMethod.GET)
    public @ResponseBody
    Result searchSongMeta(@PathVariable String prefix) {
        return songService.getSongMetaFromEs(prefix);
    }

    /**
     * 장르 유형 생성
     * @return Result.class
     */
    @RequestMapping(value="/song-genre", method = RequestMethod.GET)
    public String songGenreCreate(Model model) {

        model.addAttribute("genreId", songService.getSongGenre());
        return "meta/song_genre_create";
    }

    /**
     * 곡 전체 데이터 ElasticSearch 에 등록
     */
    @SuperAdmin
    @RequestMapping(value = "/song-es-all", method = RequestMethod.GET)
    public @ResponseBody
    Result songElasticSearchAddAll() {
        return songService.getAndPutAllSongForElasticSearch();
    }

    /**
     *  곡 MID 중복 확인
     */
    @RequestMapping(value = "/song/check/{id}", method = RequestMethod.GET)
    public @ResponseBody
    boolean songOriginIdCheck(@PathVariable Long id) {
        return songService.getCheckSongOriginId(id);
    }

    /**
     * 곡 데이터 엑셀로 내려받기
     */
    @SuperAdmin
    @RequestMapping(value = "/song/excel", method = RequestMethod.GET)
    public void songForExcel(
            HttpServletResponse response
    ) {
        String fileName = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(new Date());

        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"Songs_" + fileName + ".xlsx" + "\";");
        response.setHeader("Content-Transfer-Encoding", "binary;");

        try {
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(songService.getSongsForExcel());
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {}
    }

    /**
     * 엑셀로 곡 메타 업데이트
     * @param excel
     * @return
     */
    @SuperAdmin
    @RequestMapping(value = "/song/excel", method = RequestMethod.PUT)
    public @ResponseBody
    Result songMetaUpdateByExcel(
            @RequestPart(name = "excel", required = true)MultipartFile excel
    ) {
        return songService.xlsxToUpdateSongLangMeta(excel);
    }

}
