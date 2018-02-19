package kr.mubeat.cms.service.service.faq;

import kr.mubeat.cms.dto.CustomPageResult;
import kr.mubeat.cms.dto.CustomSearchParam;
import kr.mubeat.cms.dto.Result;
import kr.mubeat.cms.dto.service.faq.FAQDetailDTO;
import kr.mubeat.cms.enumerations.LangType;

/**
 * Created by moonkyu.lee on 2017. 9. 25..
 */
public interface FAQService {
    /**
     * 1. List
     * 2. Detail
     * 3. Add
     * 4. Update
     * 5. Delete
     */

    CustomPageResult getFAQList(CustomSearchParam searchParam);
    Result getFAQDetail(Integer faqNo, LangType langType);
    Result addFAQ(FAQDetailDTO param);
    Result updateFAQ(Integer faqNo, FAQDetailDTO param);
    Result deleteFAQ(Integer faqNo);
    Result deleteFAQLangType(Integer faqNo, LangType langType);
}
