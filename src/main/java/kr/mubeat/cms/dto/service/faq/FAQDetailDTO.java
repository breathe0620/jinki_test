package kr.mubeat.cms.dto.service.faq;

import com.querydsl.core.annotations.QueryProjection;
import kr.mubeat.cms.enumerations.LangType;

import java.util.Date;

/**
 * Created by moonkyu.lee on 2017. 9. 25..
 */
public class FAQDetailDTO {

    private Integer faqNo;
    private String category;
    private String isImportant;
    private LangType langType;
    private String subject;
    private String content;
    private Date regDt;

    @QueryProjection
    public FAQDetailDTO(
        Integer faqNo,
        String category,
        String isImportant,
        String langType,
        String subject,
        String content,
        Date regDt
    ) {
        this.faqNo = faqNo;
        this.category = category;
        this.isImportant = isImportant;
        this.langType = LangType.valueOf(langType);
        this.subject = subject;
        this.content = content;
        this.regDt = regDt;
    }

    public FAQDetailDTO() {
    }

    public Integer getFaqNo() {
        return faqNo;
    }

    public void setFaqNo(Integer faqNo) {
        this.faqNo = faqNo;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getIsImportant() {
        return isImportant;
    }

    public void setIsImportant(String isImportant) {
        this.isImportant = isImportant;
    }

    public LangType getLangType() {
        return langType;
    }

    public void setLangType(LangType langType) {
        this.langType = langType;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getRegDt() {
        return regDt;
    }

    public void setRegDt(Date regDt) {
        this.regDt = regDt;
    }
}
