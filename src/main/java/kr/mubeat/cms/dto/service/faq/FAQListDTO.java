package kr.mubeat.cms.dto.service.faq;

import com.querydsl.core.annotations.QueryProjection;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by moonkyu.lee on 2017. 9. 25..
 */
public class FAQListDTO implements Serializable {

    private static final long serialVersionUID = 4573688391917541830L;

    private Integer faqNo;
    private String category;
    private String isImportant;
    private String subject;
    private String subjectEn;
    private Date regDt;

    @QueryProjection
    public FAQListDTO(
        Integer faqNo,
        String category,
        String isImportant,
        String subject,
        String subjectEn,
        Date regDt
    ) {
        this.faqNo = faqNo;
        this.category = category;
        this.isImportant = isImportant;
        this.subject = subject;
        this.subjectEn = subjectEn;
        this.regDt = regDt;
    }

    public FAQListDTO() {
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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubjectEn() {
        return subjectEn;
    }

    public void setSubjectEn(String subjectEn) {
        this.subjectEn = subjectEn;
    }

    public Date getRegDt() {
        return regDt;
    }

    public void setRegDt(Date regDt) {
        this.regDt = regDt;
    }
}
