package kr.mubeat.cms.dto.service.notice;

import com.querydsl.core.annotations.QueryProjection;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by moonkyu.lee on 2017. 9. 25..
 */
public class NoticeListDTO implements Serializable {

    private static final long serialVersionUID = -6229542583511352321L;

    private Integer noticeNo;
    private String category;
    private String isImportant;
    private String subject;
    private String subjectEn;
    private Date regDt;

    @QueryProjection
    public NoticeListDTO(
        Integer noticeNo,
        String category,
        String isImportant,
        String subject,
        String subjectEn,
        Date regDt
    ) {
        this.noticeNo = noticeNo;
        this.category = category;
        this.isImportant = isImportant;
        this.subject = subject;
        this.subjectEn = subjectEn;
        this.regDt = regDt;
    }

    public NoticeListDTO() {
    }

    public Integer getNoticeNo() {
        return noticeNo;
    }

    public void setNoticeNo(Integer noticeNo) {
        this.noticeNo = noticeNo;
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
