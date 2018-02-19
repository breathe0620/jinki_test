package kr.mubeat.cms.domain.recommend.theme;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by moonkyu.lee on 2017. 11. 3..
 */
@Entity
@Table(name = "tbl_recommend_theme")
public class Theme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recommendthemeid", updatable = false, nullable = false)
    private Integer recommendThemeId;

    @Column(name = "position", nullable = false)
    private Integer position;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "titleen", nullable = false)
    private String titleEn;

    @Column(name = "themeimgurl")
    private String themeImgUrl;

    @Column(name = "enableyn")
    private String enableYn;

    @Column(name="createdt", insertable = false, updatable = false)
    private Date createDt;

    @Column(name="modifydt", insertable = false, updatable = false)
    private Date modifyDt;

    public Theme() {}

    public Theme(
        Integer position,
        String title,
        String titleEn,
        String themeImgUrl,
        String enableYn
    ) {
        this.position = position;
        this.title = title;
        this.titleEn = titleEn;
        this.themeImgUrl = themeImgUrl;
        this.enableYn = enableYn;
    }

    public Integer getRecommendThemeId() {
        return recommendThemeId;
    }

    public void setRecommendThemeId(Integer recommendThemeId) {
        this.recommendThemeId = recommendThemeId;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleEn() {
        return titleEn;
    }

    public void setTitleEn(String titleEn) {
        this.titleEn = titleEn;
    }

    public String getThemeImgUrl() {
        return themeImgUrl;
    }

    public void setThemeImgUrl(String themeImgUrl) {
        this.themeImgUrl = themeImgUrl;
    }

    public String getEnableYn() {
        return enableYn;
    }

    public void setEnableYn(String enableYn) {
        this.enableYn = enableYn;
    }

    public Date getCreateDt() {
        return createDt;
    }

    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
    }

    public Date getModifyDt() {
        return modifyDt;
    }

    public void setModifyDt(Date modifyDt) {
        this.modifyDt = modifyDt;
    }
}
