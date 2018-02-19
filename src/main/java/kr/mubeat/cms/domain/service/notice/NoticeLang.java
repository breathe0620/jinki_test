package kr.mubeat.cms.domain.service.notice;

import kr.mubeat.cms.domain.complexkey.NoticeLangKey;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by moonkyu.lee on 2017. 9. 25..
 */
@Entity
@IdClass(NoticeLangKey.class)
@Table(name = "tbl_notice_lang")
public class NoticeLang implements Serializable {

    private static final long serialVersionUID = -6206093168719892554L;

    @Id
    @Column(name = "noticeno", updatable = false, nullable = false)
    private Integer noticeNo;

    @Id
    @Column(name = "langtype", nullable = false)
    private String langType;

    @Column(name = "subject")
    private String subject;

    @Column(name = "content")
    private String content;

    public Integer getNoticeNo() {
        return noticeNo;
    }

    public void setNoticeNo(Integer noticeNo) {
        this.noticeNo = noticeNo;
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
