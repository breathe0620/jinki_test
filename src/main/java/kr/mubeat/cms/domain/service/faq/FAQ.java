package kr.mubeat.cms.domain.service.faq;

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
@Table(name = "tbl_faq")
public class FAQ implements Serializable {

    private static final long serialVersionUID = -2296081514211824500L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "faqno", updatable = false, nullable = false)
    private Integer faqNo;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "isimportant", nullable = false)
    private String isImportant;

    @Column(name = "regdt", insertable = false, updatable = false)
    private Date regDt;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "faqno", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private Collection<FAQLang> faqLang;

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

    public Date getRegDt() {
        return regDt;
    }

    public void setRegDt(Date regDt) {
        this.regDt = regDt;
    }

    public Collection<FAQLang> getFaqLang() {
        return faqLang;
    }

    public void setFaqLang(Collection<FAQLang> faqLang) {
        this.faqLang = faqLang;
    }
}
