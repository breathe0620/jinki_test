package kr.mubeat.cms.service.service.faq.impl;

import kr.mubeat.cms.domain.service.faq.FAQ;
import kr.mubeat.cms.domain.service.faq.FAQLang;
import kr.mubeat.cms.dto.CustomPageResult;
import kr.mubeat.cms.dto.CustomSearchParam;
import kr.mubeat.cms.dto.Result;
import kr.mubeat.cms.dto.service.faq.FAQDetailDTO;
import kr.mubeat.cms.enumerations.EnumResCode;
import kr.mubeat.cms.enumerations.LangType;
import kr.mubeat.cms.repository.service.faq.FAQLangRepository;
import kr.mubeat.cms.repository.service.faq.FAQRepository;
import kr.mubeat.cms.service.service.faq.FAQService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by moonkyu.lee on 2017. 9. 25..
 */
@Service
public class FAQServiceImpl implements FAQService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private FAQRepository faqRepository;
    private FAQLangRepository faqLangRepository;

    @Autowired
    public FAQServiceImpl(
        FAQRepository faqRepository,
        FAQLangRepository faqLangRepository
    ) {
        this.faqRepository = faqRepository;
        this.faqLangRepository = faqLangRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public CustomPageResult getFAQList(CustomSearchParam searchParam) {
        CustomPageResult pageResult = new CustomPageResult(searchParam);
        pageResult.setPageResult(faqRepository.getFAQList(searchParam));
        return pageResult;
    }

    @Override
    @Transactional(readOnly = true)
    public Result getFAQDetail(Integer faqNo, LangType langType) {
        Result result = new Result(EnumResCode.OK);
        result.setData(faqRepository.getFAQDetail(faqNo, langType));
        return result;
    }

    @Override
    @Transactional(readOnly = false)
    public Result addFAQ(FAQDetailDTO param) {
        FAQ faq = new FAQ();
        faq.setCategory(param.getCategory());
        faq.setIsImportant(param.getIsImportant());

        Integer createdId = faqRepository.save(faq).getFaqNo();

        if (createdId != null) {
            FAQLang faqLang = new FAQLang();
            faqLang.setFaqNo(createdId);
            faqLang.setLangType(param.getLangType().name());
            faqLang.setSubject(param.getSubject());
            faqLang.setContent(param.getContent());

            faqLangRepository.save(faqLang);

            return new Result(EnumResCode.OK);
        } else {
            return new Result(EnumResCode.E998);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public Result updateFAQ(Integer faqNo, FAQDetailDTO param) {
        FAQ faq = new FAQ();
        faq.setFaqNo(faqNo);
        faq.setCategory(param.getCategory());
        faq.setIsImportant(param.getIsImportant());

        faqRepository.save(faq);

        if (
            (param.getSubject() != null && param.getSubject().length() > 0)
            &&
            (param.getContent() != null && param.getContent().length() > 0)
        ) {
            FAQLang faqLang = new FAQLang();
            faqLang.setFaqNo(faqNo);
            faqLang.setLangType(param.getLangType().name());
            faqLang.setSubject(param.getSubject());
            faqLang.setContent(param.getContent());

            faqLangRepository.save(faqLang);
        }

        return new Result(EnumResCode.OK);
    }

    @Override
    @Transactional(readOnly = false)
    public Result deleteFAQ(Integer faqNo) {
        faqRepository.deleteFAQ(faqNo);

        return new Result(EnumResCode.OK);
    }

    @Override
    @Transactional(readOnly = false)
    public Result deleteFAQLangType(Integer faqNo, LangType langType) {
        faqRepository.deleteFAQLangType(faqNo, langType);

        return new Result(EnumResCode.OK);
    }
}
