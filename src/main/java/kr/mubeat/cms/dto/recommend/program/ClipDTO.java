package kr.mubeat.cms.dto.recommend.program;

import com.querydsl.core.annotations.QueryProjection;

import java.io.Serializable;

/**
 * Created by moonkyu.lee on 2017. 11. 3..
 */
public class ClipDTO implements Serializable {

    private static final long serialVersionUID = 1736473493335893579L;

    private Long clipMetaId;
    private String title;
    private String titleEn;
    private String broadDt;
    private String enableYn;

    @QueryProjection
    public ClipDTO(
            Long clipMetaId,
            String title,
            String titleEn,
            String broadDt,
            String enableYn
    ) {
        this.clipMetaId = clipMetaId;
        this.title = title;
        this.titleEn = titleEn;
        this.broadDt = broadDt;
        this.enableYn = enableYn;
    }

    public Long getClipMetaId() {
        return clipMetaId;
    }

    public void setClipMetaId(Long clipMetaId) {
        this.clipMetaId = clipMetaId;
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

    public String getBroadDt() {
        return broadDt;
    }

    public void setBroadDt(String broadDt) {
        this.broadDt = broadDt;
    }

    public String getEnableYn() {
        return enableYn;
    }

    public void setEnableYn(String enableYn) {
        this.enableYn = enableYn;
    }
}
