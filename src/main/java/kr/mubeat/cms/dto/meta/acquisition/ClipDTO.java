package kr.mubeat.cms.dto.meta.acquisition;

import com.querydsl.core.annotations.QueryProjection;

import java.io.Serializable;

/**
 * Created by moonkyu.lee on 2017. 9. 6..
 */
public class ClipDTO implements Serializable {

    private static final long serialVersionUID = 5132928491860171512L;

    private String clipId;
    private String title;
    private String synopsis;
    private String searchKeyword;
    private String clipCategory;
    private String contentImg;
    private String platformIsUse;
    private String isUse;
    private String regDate;
    private String modifyDate;
    private String fileModifyDate;
    private String broadDate;

    @QueryProjection
    public ClipDTO(
        String clipId,
        String title,
        String synopsis,
        String searchKeyword,
        String clipCategory,
        String contentImg,
        String platformIsUse,
        String isUse,
        String regDate,
        String modifyDate,
        String fileModifyDate,
        String broadDate
    ) {
        this.clipId = clipId;
        this.title = title;
        this.synopsis = synopsis;
        this.searchKeyword = searchKeyword;
        this.clipCategory = clipCategory;
        this.contentImg = contentImg;
        this.platformIsUse = platformIsUse;
        this.isUse = isUse;
        this.regDate = regDate;
        this.modifyDate = modifyDate;
        this.fileModifyDate = fileModifyDate;
        this.broadDate = broadDate;
    }

    public String getClipId() {
        return clipId;
    }

    public void setClipId(String clipId) {
        this.clipId = clipId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getSearchKeyword() {
        return searchKeyword;
    }

    public void setSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }

    public String getClipCategory() {
        return clipCategory;
    }

    public void setClipCategory(String clipCategory) {
        this.clipCategory = clipCategory;
    }

    public String getContentImg() {
        return contentImg;
    }

    public void setContentImg(String contentImg) {
        this.contentImg = contentImg;
    }

    public String getPlatformIsUse() {
        return platformIsUse;
    }

    public void setPlatformIsUse(String platformIsUse) {
        this.platformIsUse = platformIsUse;
    }

    public String getIsUse() {
        return isUse;
    }

    public void setIsUse(String isUse) {
        this.isUse = isUse;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getFileModifyDate() {
        return fileModifyDate;
    }

    public void setFileModifyDate(String fileModifyDate) {
        this.fileModifyDate = fileModifyDate;
    }

    public String getBroadDate() {
        return broadDate;
    }

    public void setBroadDate(String broadDate) {
        this.broadDate = broadDate;
    }
}
