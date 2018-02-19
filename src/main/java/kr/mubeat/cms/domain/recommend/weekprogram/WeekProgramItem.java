package kr.mubeat.cms.domain.recommend.weekprogram;

import kr.mubeat.cms.domain.complexkey.RecommendWeekProgramItemKey;
import kr.mubeat.cms.domain.meta.clip.ClipMeta;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by moonkyu.lee on 2017. 11. 3..
 */
@Entity
@IdClass(RecommendWeekProgramItemKey.class)
@Table(name = "tbl_recommend_program_item")
public class WeekProgramItem {

    @Id
    @Column(name = "recommendprogramid")
    private Integer recommendProgramId;

    @Id
    @Column(name = "clipmetaid", updatable = false, nullable = false)
    private Long clipMetaId;

    @Column(name="regdt", insertable = false, updatable = false)
    private Date regDt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clipmetaid", insertable = false, updatable = false, referencedColumnName = "clipmetaid")
    @NotFound(action = NotFoundAction.IGNORE)
    private ClipMeta clipMeta;

    public WeekProgramItem() {
    }

    public WeekProgramItem(
        Integer recommendProgramId,
        Long clipMetaId
    ) {
        this.recommendProgramId = recommendProgramId;
        this.clipMetaId = clipMetaId;
    }

    public Integer getRecommendProgramId() {
        return recommendProgramId;
    }

    public void setRecommendProgramId(Integer recommendProgramId) {
        this.recommendProgramId = recommendProgramId;
    }

    public Long getClipMetaId() {
        return clipMetaId;
    }

    public void setClipMetaId(Long clipMetaId) {
        this.clipMetaId = clipMetaId;
    }

    public Date getRegDt() {
        return regDt;
    }

    public void setRegDt(Date regDt) {
        this.regDt = regDt;
    }
}
