package kr.mubeat.cms.dto.meta.acquisition;

import com.querydsl.core.annotations.QueryProjection;

import java.io.Serializable;

/**
 * Created by moonkyu.lee on 2017. 9. 5..
 */
public class ClipMetaDTO implements Serializable {

    private static final long serialVersionUID = 601014395512344583L;

    private Long clipMetaId;
    private String clipTitle;
    private String clipCategory;
    private Integer clipClassify;
    private Integer playTime;

    private String broadDate;
    private String corporator;
    private String programTitle;

    private String enableYn;
    private String isOriginal;

    private Integer hit;

    @QueryProjection
    public ClipMetaDTO(
        Long clipMetaId,
        String clipTitle,
        String clipCategory,
        Integer clipClassify,
        Integer playTime,
        String broadDate,
        String corporator,
        String programTitle,
        String enableYn,
        String isOriginal,
        Integer hit
    ) {
        this.clipMetaId = clipMetaId;
        this.clipTitle = clipTitle;
        this.clipCategory = clipCategory;
        this.clipClassify = clipClassify;
        this.playTime = playTime;
        this.broadDate = broadDate;
        this.corporator = corporator;
        this.programTitle = programTitle;
        this.enableYn = enableYn;
        this.isOriginal = isOriginal;
        this.hit = hit;
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

    public String getClipCategory() {
        return clipCategory;
    }

    public void setClipCategory(String clipCategory) {
        this.clipCategory = clipCategory;
    }

    public Integer getClipClassify() {
        return clipClassify;
    }

    public void setClipClassify(Integer clipClassify) {
        this.clipClassify = clipClassify;
    }

    public Integer getPlayTime() {
        return playTime;
    }

    public void setPlayTime(Integer playTime) {
        this.playTime = playTime;
    }

    public String getBroadDate() {
        return broadDate;
    }

    public void setBroadDate(String broadDate) {
        this.broadDate = broadDate;
    }

    public String getCorporator() {
        return corporator;
    }

    public void setCorporator(String corporator) {
        this.corporator = corporator;
    }

    public String getProgramTitle() {
        return programTitle;
    }

    public void setProgramTitle(String programTitle) {
        this.programTitle = programTitle;
    }

    public String getEnableYn() {
        return enableYn;
    }

    public void setEnableYn(String enableYn) {
        this.enableYn = enableYn;
    }

    public String getIsOriginal() {
        return isOriginal;
    }

    public void setIsOriginal(String isOriginal) {
        this.isOriginal = isOriginal;
    }

    public Integer getHit() {
        return hit;
    }

    public void setHit(Integer hit) {
        this.hit = hit;
    }
}
