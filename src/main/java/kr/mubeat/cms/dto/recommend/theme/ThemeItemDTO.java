package kr.mubeat.cms.dto.recommend.theme;

import com.querydsl.core.annotations.QueryProjection;

import java.io.Serializable;

/**
 * Created by moonkyu.lee on 2017. 11. 6..
 */
public class ThemeItemDTO implements Serializable {

    private static final long serialVersionUID = 455777185244433262L;

    private Integer recommendThemeId;
    private Long clipMetaId;
    private String clipTitle;
    private String enableYn;

    @QueryProjection
    public ThemeItemDTO(
        Integer recommendThemeId,
        Long clipMetaId,
        String clipTitle,
        String enableYn
    ) {
        this.recommendThemeId = recommendThemeId;
        this.clipMetaId = clipMetaId;
        this.clipTitle = clipTitle;
        this.enableYn = enableYn;
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

    public String getClipTitle() {
        return clipTitle;
    }

    public void setClipTitle(String clipTitle) {
        this.clipTitle = clipTitle;
    }

    public String getEnableYn() {
        return enableYn;
    }

    public void setEnableYn(String enableYn) {
        this.enableYn = enableYn;
    }
}
