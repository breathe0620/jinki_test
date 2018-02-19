package kr.mubeat.cms.dto.meta.acquisition;

import com.querydsl.core.annotations.QueryProjection;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by doohwan.yoo on 2017. 4. 30..
 */
public class AcquisitionDTO implements Serializable {

    private static final long serialVersionUID = -2809776539143723479L;

    @QueryProjection
    public AcquisitionDTO(
            Long clipmetaid,
            String clipId,
            String corporator,
            String programTitle,
            String clipTitle,
            String clipCategory,
            String contentNumber,
            String uploadyn,
            String transcodeyn,
            Date createDt,
            Date modifyDt) {
        this.clipMetaId = clipmetaid;
        this.clipId = clipId;
        this.corporator = corporator;
        this.programTitle = programTitle;
        this.clipTitle = clipTitle;
        this.clipCategory = clipCategory;
        this.contentNumber = contentNumber;
        this.uploadyn = uploadyn;
        this.transcodeyn = transcodeyn;
        this.createDt = createDt;
        this.modifyDt = modifyDt;
    }

    @QueryProjection
    public AcquisitionDTO(
            Long clipmetaid,
            String corporator,
            String clipId,
            String programTitle,
            String clipTitle,
            String clipCategory,
            String contentNumber,
            String uploadyn,
            String transcodeyn,
            Date createDt,
            Date modifyDt,
            String broadDt) {
        this.clipMetaId = clipmetaid;
        this.clipId = clipId;
        this.corporator = corporator;
        this.programTitle = programTitle;
        this.clipTitle = clipTitle;
        this.clipCategory = clipCategory;
        this.contentNumber = contentNumber;
        this.uploadyn = uploadyn;
        this.transcodeyn = transcodeyn;
        this.createDt = createDt;
        this.modifyDt = modifyDt;
        this.broadDt = broadDt;
    }

    public AcquisitionDTO() {}

    private Long clipMetaId;
    private String clipId;
    private String corporator;
    private String programTitle;
    private String clipTitle;
    private String clipCategory;
    private String contentNumber;
    private String uploadyn;
    private String transcodeyn;
    private String thumbImgUrl;
    private Integer playtime;
    private Date createDt;
    private Date modifyDt;
    private String broadDt;

    public Long getClipMetaId() {
        return clipMetaId;
    }

    public void setClipMetaId(Long clipMetaId) {
        this.clipMetaId = clipMetaId;
    }

    public String getClipId() {
        return clipId;
    }

    public void setClipId(String clipId) {
        this.clipId = clipId;
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

    public String getContentNumber() {
        return contentNumber;
    }

    public void setContentNumber(String contentNumber) {
        this.contentNumber = contentNumber;
    }

    public String getUploadyn() {
        return uploadyn;
    }

    public void setUploadyn(String uploadyn) {
        this.uploadyn = uploadyn;
    }

    public String getTranscodeyn() {
        return transcodeyn;
    }

    public void setTranscodeyn(String transcodeyn) {
        this.transcodeyn = transcodeyn;
    }

    public String getThumbImgUrl() {
        return thumbImgUrl;
    }

    public void setThumbImgUrl(String thumbImgUrl) {
        this.thumbImgUrl = thumbImgUrl;
    }

    public Integer getPlaytime() {
        return playtime;
    }

    public void setPlaytime(Integer playtime) {
        this.playtime = playtime;
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

    public String getBroadDt() {
        return broadDt;
    }

    public void setBroadDt(String broadDt) {
        this.broadDt = broadDt;
    }
}
