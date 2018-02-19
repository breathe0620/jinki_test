package kr.mubeat.cms.repository.service.faq;

import kr.mubeat.cms.dto.CustomSearchParam;
import kr.mubeat.cms.dto.service.faq.FAQDetailDTO;
import kr.mubeat.cms.dto.service.faq.FAQListDTO;
import kr.mubeat.cms.enumerations.LangType;
import org.springframework.data.domain.Page;

/**
 * Created by moonkyu.lee on 2017. 9. 27..
 */
public interface FAQRepositoryCustom {

    Page<FAQListDTO> getFAQList(CustomSearchParam searchParam);
    FAQDetailDTO getFAQDetail(Integer faqNo, LangType langType);
    void deleteFAQ(Integer faqNo);
    void deleteFAQLangType(Integer faqNo, LangType langType);

}
