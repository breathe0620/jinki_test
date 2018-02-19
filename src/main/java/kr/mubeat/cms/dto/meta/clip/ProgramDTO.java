package kr.mubeat.cms.dto.meta.clip;

import com.querydsl.core.annotations.QueryProjection;

import java.io.Serializable;

/**
 * Created by doohwan.yoo on 2017. 5. 10..
 */
public class ProgramDTO implements Serializable {

    private static final long serialVersionUID = -8235034347509091486L;

    @QueryProjection
    public ProgramDTO(
            String programId,
            String corporator,
            String programCode,
            String title,
            String searchKeyword,
            String homepageUrl
    ) {
        this.programId = programId;
        this.corporator = corporator;
        this.programCode = programCode;
        this.title = title;
        this.searchKeyword = searchKeyword;
        this.homepageUrl = homepageUrl;
    }

    public ProgramDTO(String programId, String corporator, String channelId, String title, String searchKeyword,
                      String programImg, String programPosterImg, String programPosterImg2, String programBannerImg,
                      String programThumImg) {

        this.programId = programId;
        this.corporator = corporator;
        this.title = title;
        this.channelId = channelId;
        this.searchKeyword = searchKeyword;
        this.programImg = programImg;
        this.programPosterImg = programPosterImg;
        this.programPosterImg2 = programPosterImg2;
        this.programBannerImg = programBannerImg;
        this.programThumImg = programThumImg;
    }

    private String programId;
    private String corporator;
    private String channelId;
    private String programCode;
    private String title;
    private String searchKeyword;
    private String homepageUrl;
    private String programImg;
    private String programPosterImg;
    private String programPosterImg2;
    private String programBannerImg;
    private String programThumImg;

    public String getProgramCode() {
        return programCode;
    }

    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }

    public String getHomepageUrl() {
        return homepageUrl;
    }

    public void setHomepageUrl(String homepageUrl) {
        this.homepageUrl = homepageUrl;
    }

    public String getProgramId() {
        return programId;
    }

    public void setProgramId(String programId) {
        this.programId = programId;
    }

    public String getCorporator() {
        return corporator;
    }

    public void setCorporator(String corporator) {
        this.corporator = corporator;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSearchKeyword() {
        return searchKeyword;
    }

    public void setSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }

    public String getProgramImg() {
        return programImg;
    }

    public void setProgramImg(String programImg) {
        this.programImg = programImg;
    }

    public String getProgramPosterImg() {
        return programPosterImg;
    }

    public void setProgramPosterImg(String programPosterImg) {
        this.programPosterImg = programPosterImg;
    }

    public String getProgramPosterImg2() {
        return programPosterImg2;
    }

    public void setProgramPosterImg2(String programPosterImg2) {
        this.programPosterImg2 = programPosterImg2;
    }

    public String getProgramBannerImg() {
        return programBannerImg;
    }

    public void setProgramBannerImg(String programBannerImg) {
        this.programBannerImg = programBannerImg;
    }

    public String getProgramThumImg() {
        return programThumImg;
    }

    public void setProgramThumImg(String programThumImg) {
        this.programThumImg = programThumImg;
    }
}
