package kr.mubeat.cms.domain.service.faq;

import kr.mubeat.cms.domain.complexkey.FAQLangKey;

import javax.persistence.*;

/**
 * Created by moonkyu.lee on 2017. 9. 25..
 */
@Entity
@IdClass(FAQLangKey.class)
@Table(name = "tbl_faq_lang")
public class FAQLang {

    @Id
    @Column(name = "faqno", updatable = false, nullable = false)
    private Integer faqNo;

    @Id
    @Column(name = "langtype", nullable = false)
    private String langType;

    @Column(name = "subject")
    private String subject;

    @Column(name = "content")
    private String content;

    public Integer getFaqNo() {
        return faqNo;
    }

    public void setFaqNo(Integer faqNo) {
        this.faqNo = faqNo;
    }

    public String getLangType() {
        return langType;
    }

    public void setLangType(String langType) {
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
}
