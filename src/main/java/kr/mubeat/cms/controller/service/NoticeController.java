package kr.mubeat.cms.controller.service;

import kr.mubeat.cms.annotation.admin.SuperAdmin;
import kr.mubeat.cms.dto.CustomSearchParam;
import kr.mubeat.cms.dto.Result;
import kr.mubeat.cms.dto.service.notice.NoticeDetailDTO;
import kr.mubeat.cms.enumerations.LangType;
import kr.mubeat.cms.service.service.notice.NoticeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

/**
 * Created by moonkyu.lee on 2017. 9. 25..
 */
@SuperAdmin
@Controller
@RequestMapping(value = "/service")
@RolesAllowed("MENU_MEMBER")
public class NoticeController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private NoticeService noticeService;

    @Autowired
    public NoticeController(
        NoticeService noticeService
    ) {
        this.noticeService = noticeService;
    }

    /**
     * 공지사항 리스트 페이지 요청
     * @param model
     * @param searchParam
     * @return
     */
    @RequestMapping(value = "/notices", method = RequestMethod.GET)
    public String noticeView(
        Model model,
        @ModelAttribute CustomSearchParam searchParam
    ) {
        model.addAttribute("result", noticeService.getNoticeList(searchParam));
        return "service/notice_list";
    }

    /**
     * 공지사항 등록 페이지 요청
     * @return
     */
    @RequestMapping(value = "/notice", method = RequestMethod.GET)
    public String noticeAddView() {
        return "service/notice_create";
    }

    /**
     * 공지사항 아이템 상세 보기
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/notice/{id}", method = RequestMethod.GET)
    public String detailNoticeView(
        Model model,
        @PathVariable Integer id
    ) {
        Result result = noticeService.getNoticeDetail(id, LangType.EN);
        if (result.getData() == null) {
            result = noticeService.getNoticeDetail(id, LangType.KO);
        }
        model.addAttribute("detail", result);
        return "service/notice_detail";
    }

    /**
     * 공지사항 아이템 언어별 상세 보기
     * @param id
     * @param langtype
     * @return
     */
    @RequestMapping(value = "/notice/{id}/{langtype}", method = RequestMethod.GET)
    public @ResponseBody
    Result detailNotice(
        @PathVariable Integer id,
        @PathVariable LangType langtype
    ) {
        return noticeService.getNoticeDetail(id, langtype);
    }

    /**
     * 공지사항 등록
     * @param param
     * @return
     */
    @RequestMapping(value = "/notice", method = RequestMethod.POST)
    public @ResponseBody
    Result addNotice(
        @RequestBody NoticeDetailDTO param
    ) {
        return noticeService.addNotice(param);
    }

    /**
     * 공지사항 업데이트
     * @param id
     * @param param
     * @return
     */
    @RequestMapping(value = "/notice/{id}", method = RequestMethod.PUT)
    public @ResponseBody
    Result updateNotice(
        @PathVariable Integer id,
        @RequestBody NoticeDetailDTO param
    ) {
        return noticeService.updateNotice(id, param);
    }

    /**
     * 공지사항 아이템 삭제
     * @param id
     * @return
     */
    @RequestMapping(value = "/notice/{id}", method = RequestMethod.DELETE)
    public @ResponseBody
    Result deleteNotice(
        @PathVariable Integer id
    ) {
        return noticeService.deleteNotice(id);
    }

    /**
     * 공지사항 아이템 언어별 삭제
     * @param id
     * @param langtype
     * @return
     */
    @RequestMapping(value = "/notice/{id}/{langtype}", method = RequestMethod.DELETE)
    public @ResponseBody
    Result deleteNoticeLangType(
        @PathVariable Integer id,
        @PathVariable LangType langtype
    ) {
        return noticeService.deleteNoticeLangType(id, langtype);
    }

}