package kr.mubeat.cms.domain.service.notice;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 * Created by moonkyu.lee on 2017. 9. 25..
 */
@Entity
@Table(name = "tbl_notice")
public class Notice implements Serializable {

    private static final long serialVersionUID = 4255006580082427017L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "noticeno", updatable = false, nullable = false)
    private Integer noticeNo;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "isimportant", nullable = false)
    private String isImportant;

    @Column(name = "regdt", insertable = false, updatable = false)
    private Date regDt;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "noticeno", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private Collection<NoticeLang> noticeLang;

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

    public Date getRegDt() {
        return regDt;
    }

    public void setRegDt(Date regDt) {
        this.regDt = regDt;
    }

    public Collection<NoticeLang> getNoticeLang() {
        return noticeLang;
    }

    public void setNoticeLang(Collection<NoticeLang> noticeLang) {
        this.noticeLang = noticeLang;
    }
}
