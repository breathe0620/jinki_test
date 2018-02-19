package kr.mubeat.cms.dto.recommend.theme;

import com.querydsl.core.annotations.QueryProjection;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by moonkyu.lee on 2017. 11. 6..
 */
public class ThemeDTO implements Serializable {

    private static final long serialVersionUID = 3522181850475079343L;

    private Integer recommendThemeId;
    private Integer position;
    private String title;
    private String titleEn;
    private String themeImgUrl;
    private String enableYn;
    private Date createDt;
    private Date modifyDt;

    public ThemeDTO(
            Integer position,
            String title,
            String titleEn,
            String enableYn
    ) {
        this.position = position;
        this.title = title;
        this.titleEn = titleEn;
        this.enableYn = enableYn;
    }

    @QueryProjection
    public ThemeDTO(
        Integer recommendThemeId,
        Integer position,
        String title,
        String titleEn,
        String themeImgUrl,
        String enableYn,
        Date createDt,
        Date modifyDt
    ) {
        this.recommendThemeId = recommendThemeId;
        this.position = position;
        this.title = title;
        this.titleEn = titleEn;
        this.themeImgUrl = themeImgUrl;
        this.enableYn = enableYn;
        this.createDt = createDt;
        this.modifyDt = modifyDt;
    }

    public ThemeDTO(){}

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
