package kr.mubeat.cms.controller.service;

import kr.mubeat.cms.annotation.admin.SuperAdmin;
import kr.mubeat.cms.dto.CustomSearchParam;
import kr.mubeat.cms.dto.Result;
import kr.mubeat.cms.dto.service.faq.FAQDetailDTO;
import kr.mubeat.cms.enumerations.LangType;
import kr.mubeat.cms.service.service.faq.FAQService;
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
public class FAQController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private FAQService faqService;

    @Autowired
    public FAQController(
        FAQService faqService
    ) {
        this.faqService = faqService;
    }

    /**
     * FAQ 리스트 요청
     * @param model
     * @param searchParam
     * @return
     */
    @RequestMapping(value = "/faqs", method = RequestMethod.GET)
    public String faqView(
        Model model,
        @ModelAttribute CustomSearchParam searchParam
    ) {
        model.addAttribute("result", faqService.getFAQList(searchParam));
        return "service/faq_list";
    }

    /**
     * FAQ 등록 페이지 요청
     * @return
     */
    @RequestMapping(value = "/faq", method = RequestMethod.GET)
    public String faqAddView() {
        return "service/faq_create";
    }

    /**
     * FAQ 아이템 상세 보기
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/faq/{id}", method = RequestMethod.GET)
    public String detailFaqView(
        Model model,
        @PathVariable Integer id
    ) {
        Result result = faqService.getFAQDetail(id, LangType.EN);
        if (result.getData() == null) {
            result = faqService.getFAQDetail(id, LangType.KO);
        }
        model.addAttribute("detail", result);
        return "service/faq_detail";
    }

    /**
     * FAQ 아이템 언어별 상세 보기
     * @param id
     * @param langtype
     * @return
     */
    @RequestMapping(value = "/faq/{id}/{langtype}", method = RequestMethod.GET)
    public @ResponseBody
    Result detailFaq(
        @PathVariable Integer id,
        @PathVariable LangType langtype
    ) {
        return faqService.getFAQDetail(id, langtype);
    }

    /**
     * FAQ 아이템 등록
     * @param param
     * @return
     */
    @RequestMapping(value = "/faq", method = RequestMethod.POST)
    public @ResponseBody
    Result addFaq(
        @RequestBody FAQDetailDTO param
    ) {
        return faqService.addFAQ(param);
    }

    /**
     * FAQ 아이템 업데이트
     * @param id
     * @param param
     * @return
     */
    @RequestMapping(value = "/faq/{id}", method = RequestMethod.PUT)
    public @ResponseBody
    Result updateFaq(
        @PathVariable Integer id,
        @RequestBody FAQDetailDTO param
    ) {
        return faqService.updateFAQ(id, param);
    }

    /**
     * FAQ 아이템 삭제
     * @param id
     * @return
     */
    @RequestMapping(value = "/faq/{id}", method = RequestMethod.DELETE)
    public @ResponseBody
    Result deleteFaq(
        @PathVariable Integer id
    ) {
        return faqService.deleteFAQ(id);
    }

    /**
     * FAQ 아이템 언어별 삭제
     * @param id
     * @param langType
     * @return
     */
    @RequestMapping(value = "/faq/{id}/{langtype}", method = RequestMethod.DELETE)
    public @ResponseBody
    Result deleteFaqLangType(
        @PathVariable Integer id,
        @PathVariable LangType langType
    ) {
        return faqService.deleteFAQLangType(id, langType);
    }
}