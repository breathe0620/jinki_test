package kr.mubeat.cms.dto.meta.acquisition;

import com.querydsl.core.annotations.QueryProjection;

import java.io.Serializable;

/**
 * Created by moonkyu.lee on 2017. 9. 6..
 */
public class ClipMetaLangDTO implements Serializable {

    private static final long serialVersionUID = 3363523540063686198L;

    private Long clipMetaId;
    private String langType;
    private String clipTitle;
    private String programTitle;

    @QueryProjection
    public ClipMetaLangDTO(
        Long clipMetaId,
        String langType,
        String clipTitle,
        String programTitle
    ) {
        this.clipMetaId = clipMetaId;
        this.langType = langType;
        this.clipTitle = clipTitle;
        this.programTitle = programTitle;
    }

    public Long getClipMetaId() {
        return clipMetaId;
    }

    public void setClipMetaId(Long clipMetaId) {
        this.clipMetaId = clipMetaId;
    }

    public String getLangType() {
        return langType;
    }

    public void setLangType(String langType) {
        this.langType = langType;
    }

    public String getClipTitle() {
        return clipTitle;
    }

    public void setClipTitle(String clipTitle) {
        this.clipTitle = clipTitle;
    }

    public String getProgramTitle() {
        return programTitle;
    }

    public void setProgramTitle(String programTitle) {
        this.programTitle = programTitle;
    }
}
