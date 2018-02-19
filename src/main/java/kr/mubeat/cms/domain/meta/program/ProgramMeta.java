package kr.mubeat.cms.domain.meta.program;

import kr.mubeat.cms.domain.meta.clip.ClipMeta;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

/**
 * Created by moonkyu.lee on 2017. 9. 21..
 */
@Entity
@Table(name = "tbl_program_meta")
public class ProgramMeta implements Serializable {

    private static final long serialVersionUID = -1671915592898635410L;

    @Id
    @Column(name = "programid", nullable = false)
    private String programId;

    @Column(name = "corporator", nullable = false)
    private String corporator;

    @Column(name = "programtitle")
    private String programTitle;

    @Column(name = "programtitle_en")
    private String programTitleEn;

    @Column(name = "limitnation")
    private String limitnation;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "programid", insertable = false, updatable = false, referencedColumnName = "programid")
    @NotFound(action = NotFoundAction.IGNORE)
    private Collection<ClipMeta> clipMeta;

    public String getProgramId() {
        return programId;
    }

    public void setProgramId(String programId) {
        this.programId = programId;
    }

    public String getCorporator() {
        return corporator;
    }

    public void setCorporator(String corporator) {
        this.corporator = corporator;
    }

    public String getProgramTitle() {
        return programTitle;
    }

    public void setProgramTitle(String programTitle) {
        this.programTitle = programTitle;
    }

    public String getProgramTitleEn() {
        return programTitleEn;
    }

    public void setProgramTitleEn(String programTitleEn) {
        this.programTitleEn = programTitleEn;
    }

    public String getLimitnation() {
        return limitnation;
    }

    public void setLimitnation(String limitnation) {
        this.limitnation = limitnation;
    }

    public Collection<ClipMeta> getClipMeta() {
        return clipMeta;
    }

    public void setClipMeta(Collection<ClipMeta> clipMeta) {
        this.clipMeta = clipMeta;
    }
}
