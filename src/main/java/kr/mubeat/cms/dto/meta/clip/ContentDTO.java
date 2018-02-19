package kr.mubeat.cms.dto.meta.clip;

import java.io.Serializable;

/**
 * Created by doohwan.yoo on 2017. 5. 10..
 */
public class ContentDTO implements Serializable {

    private static final long serialVersionUID = 968655160321712555L;

    public ContentDTO(String contentId, String contentNumber, String broadDate, String actor, String searchKeyword, String contentImg) {
        this.contentId = contentId;
        this.contentNumber = contentNumber;
        this.broadDate = broadDate;
        this.actor = actor;
        this.searchKeyword = searchKeyword;
        this.contentImg = contentImg;
    }

    private String contentId;
    private String contentNumber;
    private String broadDate;
    private String actor;
    private String searchKeyword;
    private String contentImg;

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getContentNumber() {
        return contentNumber;
    }

    public void setContentNumber(String contentNumber) {
        this.contentNumber = contentNumber;
    }

    public String getBroadDate() {
        return broadDate;
    }

    public void setBroadDate(String broadDate) {
        this.broadDate = broadDate;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getSearchKeyword() {
        return searchKeyword;
    }

    public void setSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }

    public String getContentImg() {
        return contentImg;
    }

    public void setContentImg(String contentImg) {
        this.contentImg = contentImg;
    }
}
