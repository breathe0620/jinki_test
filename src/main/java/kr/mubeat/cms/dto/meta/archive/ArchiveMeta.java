package kr.mubeat.cms.dto.meta.archive;

import java.io.Serializable;

/**
 * Created by moonkyu.lee on 2017. 9. 21..
 */
public class ArchiveMeta implements Serializable {

    private static final long serialVersionUID = -9164733343518374487L;

    /**
     * 원본
     */
    private String originXML;
    private String originVideo;

    /**
     * tbl_clip_acquisition
     */
    private Long clipMetaId;
    private String providerType;

    /**
     * tbl_clip_meta
     */
    private String corporator;
    private String clipCategory;
    private Integer playtime;
    private String thumbImgUrl;
    private String broadDate;
    private String programId;
    private Integer agencyId = 0;

    /**
     * tbl_clip_meta_lang
     */
    private String clipTitle;
    private String clipTitleEn;

    public ArchiveMeta() {
    }

    public String getOriginXML() {
        return originXML;
    }

    public void setOriginXML(String originXML) {
        this.originXML = originXML;
    }

    public String getOriginVideo() {
        return originVideo;
    }

    public void setOriginVideo(String originVideo) {
        this.originVideo = originVideo;
    }

    public Long getClipMetaId() {
        return clipMetaId;
    }

    public void setClipMetaId(Long clipMetaId) {
        this.clipMetaId = clipMetaId;
    }

    public String getProviderType() {
        return providerType;
    }

    public void setProviderType(String providerType) {
        this.providerType = providerType;
    }

    public String getCorporator() {
        return corporator;
    }

    public void setCorporator(String corporator) {
        this.corporator = corporator;
    }

    public String getClipCategory() {
        return clipCategory;
    }

    public void setClipCategory(String clipCategory) {
        this.clipCategory = clipCategory;
    }

    public Integer getPlaytime() {
        return playtime;
    }

    public void setPlaytime(Integer playtime) {
        this.playtime = playtime;
    }

    public String getThumbImgUrl() {
        return thumbImgUrl;
    }

    public void setThumbImgUrl(String thumbImgUrl) {
        this.thumbImgUrl = thumbImgUrl;
    }

    public String getBroadDate() {
        return broadDate;
    }

    public void setBroadDate(String broadDate) {
        this.broadDate = broadDate;
    }

    public String getProgramId() {
        return programId;
    }

    public void setProgramId(String programId) {
        this.programId = programId;
    }

    public Integer getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Integer agencyId) {
        this.agencyId = agencyId;
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
}
