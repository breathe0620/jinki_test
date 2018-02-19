package kr.mubeat.cms.dto.meta.acquisition;

import com.querydsl.core.annotations.QueryProjection;

import java.io.Serializable;

/**
 * Created by moonkyu.lee on 2017. 9. 5..
 */
public class AcquisitionListDTO implements Serializable {

    private static final long serialVersionUID = -506447628550215465L;

    private Long clipMetaId;
    private String corporator;
    private String programTitle;
    private String clipCategory;
    private String clipTitle;
    private String uploadYn;
    private String transcodeYn;
    private String broadDate;

    @QueryProjection
    public AcquisitionListDTO(
        Long clipMetaId,
        String corporator,
        String programTitle,
        String clipCategory,
        String clipTitle,
        String uploadYn,
        String transcodeYn,
        String broadDate
    ) {
        this.clipMetaId = clipMetaId;
        this.corporator = corporator;
        this.programTitle = programTitle;
        this.clipCategory = clipCategory;
        this.clipTitle = clipTitle;
        this.uploadYn = uploadYn;
        this.transcodeYn = transcodeYn;
        this.broadDate = broadDate;
    }

    public Long getClipMetaId() {
        return clipMetaId;
    }

    public void setClipMetaId(Long clipMetaId) {
        this.clipMetaId = clipMetaId;
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

    public String getClipCategory() {
        return clipCategory;
    }

    public void setClipCategory(String clipCategory) {
        this.clipCategory = clipCategory;
    }

    public String getClipTitle() {
        return clipTitle;
    }

    public void setClipTitle(String clipTitle) {
        this.clipTitle = clipTitle;
    }

    public String getUploadYn() {
        return uploadYn;
    }

    public void setUploadYn(String uploadYn) {
        this.uploadYn = uploadYn;
    }

    public String getTranscodeYn() {
        return transcodeYn;
    }

    public void setTranscodeYn(String transcodeYn) {
        this.transcodeYn = transcodeYn;
    }

    public String getBroadDate() {
        return broadDate;
    }

    public void setBroadDate(String broadDate) {
        this.broadDate = broadDate;
    }
}
