package kr.mubeat.cms.domain.recommend.theme;

import kr.mubeat.cms.domain.complexkey.RecommendThemeItemKey;
import kr.mubeat.cms.domain.meta.clip.ClipMeta;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by moonkyu.lee on 2017. 11. 3..
 */
@Entity
@IdClass(RecommendThemeItemKey.class)
@Table(name = "tbl_recommend_theme_item")
public class ThemeItem implements Serializable {

    private static final long serialVersionUID = -5336656621370025849L;

    @Id
    @Column(name = "recommendthemeid")
    private Integer recommendThemeId;

    @Id
    @Column(name = "clipmetaid")
    private Long clipMetaId;

    @Column(name = "regdt", insertable = false, updatable = false)
    private Date regDt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clipmetaid", insertable = false, updatable = false, referencedColumnName = "clipmetaid")
    @NotFound(action = NotFoundAction.IGNORE)
    private ClipMeta clipMeta;

    public ThemeItem() {
    }

    public ThemeItem(
        Integer recommendThemeId,
        Long clipMetaId
    ) {
        this.recommendThemeId = recommendThemeId;
        this.clipMetaId = clipMetaId;
    }

    public Integer getRecommendThemeId() {
        return recommendThemeId;
    }

    public void setRecommendThemeId(Integer recommendThemeId) {
        this.recommendThemeId = recommendThemeId;
    }

    public Long getClipMetaId() {
        return clipMetaId;
    }

    public void setClipMetaId(Long clipMetaId) {
        this.clipMetaId = clipMetaId;
    }

    public ClipMeta getClipMeta() {
        return clipMeta;
    }

    public void setClipMeta(ClipMeta clipMeta) {
        this.clipMeta = clipMeta;
    }

    public Date getRegDt() {
        return regDt;
    }

    public void setRegDt(Date regDt) {
        this.regDt = regDt;
    }
}
