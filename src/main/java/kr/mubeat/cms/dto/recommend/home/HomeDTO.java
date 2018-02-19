package kr.mubeat.cms.dto.recommend.home;

import com.querydsl.core.annotations.QueryProjection;
import kr.mubeat.cms.domain.recommend.home.Home;

import java.io.Serializable;

/**
 * Created by moonkyu.lee on 2017. 11. 3..
 */
public class HomeDTO implements Serializable {

    private static final long serialVersionUID = 2102410312979731395L;

    private Integer recommendHomeId;
    private Long clipMetaId;
    private String clipTitle;
    private String clipTitleEn;
    private String enableYn;

    @QueryProjection
    public HomeDTO(
        Integer recommendHomeId,
        Long clipMetaId,
        String clipTitle,
        String clipTitleEn,
        String enableYn
    ) {
        this.recommendHomeId = recommendHomeId;
        this.clipMetaId = clipMetaId;
        this.clipTitle = clipTitle;
        this.clipTitleEn = clipTitleEn;
        this.enableYn = enableYn;
    }

    public HomeDTO(){}

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

    public String getClipTitle() {
        return clipTitle;
    }

    public void setClipTitle(String clipTitle) {
        this.clipTitle = clipTitle;
    }

    public String getClipTitleEn() {
        return clipTitleEn;
    }

    public void setClipTitleEn(String clipTitleEn) {
        this.clipTitleEn = clipTitleEn;
    }

    public String getEnableYn() {
        return enableYn;
    }

    public void setEnableYn(String enableYn) {
        this.enableYn = enableYn;
    }
}
