package kr.mubeat.cms.domain.recommend.weekprogram;

import kr.mubeat.cms.domain.meta.program.ProgramMeta;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by moonkyu.lee on 2017. 11. 3..
 */
@Entity
@Table(name = "tbl_recommend_program")
public class WeekProgram implements Serializable {

    private static final long serialVersionUID = 7855961529920271562L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recommendprogramid", updatable = false, nullable = false)
    private Integer recommendProgramId;

    @Column(name = "programid", nullable = false)
    private String programId;

    @Column(name = "bannerimgurl", nullable = false)
    private String bannerImgUrl;

    @Column(name="modifydt", insertable = false, updatable = false)
    private Date modifyDt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "programid", insertable = false, updatable = false, referencedColumnName = "programid")
    @NotFound(action = NotFoundAction.IGNORE)
    private ProgramMeta programMeta;

    public WeekProgram() {
    }

    public WeekProgram(
        Integer recommendProgramId,
        String programId,
        String bannerImgUrl
    ) {
        this.recommendProgramId = recommendProgramId;
        this.programId = programId;
        this.bannerImgUrl = bannerImgUrl;
    }

    public Integer getRecommendProgramId() {
        return recommendProgramId;
    }

    public void setRecommendProgramId(Integer recommendProgramId) {
        this.recommendProgramId = recommendProgramId;
    }

    public String getProgramId() {
        return programId;
    }

    public void setProgramId(String programId) {
        this.programId = programId;
    }

    public String getBannerImgUrl() {
        return bannerImgUrl;
    }

    public void setBannerImgUrl(String bannerImgUrl) {
        this.bannerImgUrl = bannerImgUrl;
    }

    public Date getModifyDt() {
        return modifyDt;
    }

    public void setModifyDt(Date modifyDt) {
        this.modifyDt = modifyDt;
    }

    public ProgramMeta getProgramMeta() {
        return programMeta;
    }

    public void setProgramMeta(ProgramMeta programMeta) {
        this.programMeta = programMeta;
    }
}
