package kr.mubeat.cms.domain.recommend.home;

import io.swagger.annotations.ApiModelProperty;
import kr.mubeat.cms.domain.meta.clip.ClipMeta;
import kr.mubeat.cms.dto.recommend.home.ClipDTO;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by moonkyu.lee on 2017. 11. 2..
 */
@Entity
@SqlResultSetMapping(
    name = "HomeClipDTO",
    classes = {
        @ConstructorResult(
            targetClass = ClipDTO.class,
            columns = {
                @ColumnResult(name = "clipmetaid", type = Long.class)
                ,@ColumnResult(name = "cliptitle", type = String.class)
                ,@ColumnResult(name = "cliptitleen", type = String.class)
                ,@ColumnResult(name = "enableyn", type = String.class)
            }
        )
    }
)
@Table(name = "tbl_recommend_home")
public class Home implements Serializable {

    private static final long serialVersionUID = 3404801318824770519L;

    @Id
    @Column(name = "recommendhomeid", nullable = false)
    @ApiModelProperty(hidden = true)
    private Integer recommendHomeId;

    @Column(name = "clipmetaid", nullable = false)
    private Long clipMetaId;

    @Column(name = "regdt", insertable = false, updatable = false)
    private Date regDt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clipmetaid", insertable = false, updatable = false, referencedColumnName = "clipmetaid")
    @NotFound(action = NotFoundAction.IGNORE)
    private ClipMeta clipMeta;

    public Integer getRecommendHomeId() {
        return recommendHomeId;
    }

    public void setRecommendHomeId(Integer recommendHomeId) {
        this.recommendHomeId = recommendHomeId;
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

    public ClipMeta getClipMeta() {
        return clipMeta;
    }

    public void setClipMeta(ClipMeta clipMeta) {
        this.clipMeta = clipMeta;
    }
}
