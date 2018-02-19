package kr.mubeat.cms.domain.meta.clip;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by doohwan.yoo on 2017. 4. 27..
 */
@Entity
@Table(name = "tbl_program")
public class Program extends BaseInfo implements Serializable {

    private static final long serialVersionUID = 5237516493420641020L;

    @Id
    @Column(name = "programid", nullable = false)
    private String programId;

    @Column(name = "corporator", nullable = false)
    private String corporator;

    @Column(name = "eventcode")
    private String eventCode;

    @Column(name = "programcode", nullable = false)
    private String programCode;

    @Column(name = "section", nullable = false)
    private String section;

    @Column(name = "subcategory")
    private String subCategory;

    @Column(name = "channelid")
    private String channelId;

    @Column(name = "startdate")
    private String startDate;

    @Column(name = "enddate")
    private String endDate;

    @Column(name = "weekcode")
    private String weekCode;

    @Column(name = "starttime")
    private String startTime;

    @Column(name = "endtime")
    private String endTime;

    @Column(name = "homepageurl")
    private String homepageUrl;

    @Column(name = "reviewurl")
    private String reviewUrl;

    @Column(name = "bbsurl")
    private String bbsUrl;

    @Column(name = "linktitle1")
    private String linkTitle1;

    @Column(name = "linkurl1")
    private String linkurl1;

    @Column(name = "linktitle2")
    private String linkTitle2;

    @Column(name = "linkurl2")
    private String linkurl2;

    @Column(name = "linktitle3")
    private String linkTitle3;

    @Column(name = "linkurl3")
    private String linkurl3;

    @Column(name = "cpid", nullable = false)
    private String cpId;

    @Column(name = "programimg")
    private String programImg;

    @Column(name = "programposterimg")
    private String programPosterImg;

    @Column(name = "programposterimg2")
    private String programposterimg2;

    @Column(name = "programbannerimg")
    private String programBannerImg;

    @Column(name = "programthumimg")
    private String programThumImg;

    @Column(name = "actor")
    private String actor;

    @Column(name = "director")
    private String director;

    @Column(name = "writer")
    private String writer;

    @Column(name = "platformisuse", nullable = false)
    private String platformIsUse;

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

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    public String getProgramCode() {
        return programCode;
    }

    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getWeekCode() {
        return weekCode;
    }

    public void setWeekCode(String weekCode) {
        this.weekCode = weekCode;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getHomepageUrl() {
        return homepageUrl;
    }

    public void setHomepageUrl(String homepageUrl) {
        this.homepageUrl = homepageUrl;
    }

    public String getReviewUrl() {
        return reviewUrl;
    }

    public void setReviewUrl(String reviewUrl) {
        this.reviewUrl = reviewUrl;
    }

    public String getBbsUrl() {
        return bbsUrl;
    }

    public void setBbsUrl(String bbsUrl) {
        this.bbsUrl = bbsUrl;
    }

    public String getLinkTitle1() {
        return linkTitle1;
    }

    public void setLinkTitle1(String linkTitle1) {
        this.linkTitle1 = linkTitle1;
    }

    public String getLinkurl1() {
        return linkurl1;
    }

    public void setLinkurl1(String linkurl1) {
        this.linkurl1 = linkurl1;
    }

    public String getLinkTitle2() {
        return linkTitle2;
    }

    public void setLinkTitle2(String linkTitle2) {
        this.linkTitle2 = linkTitle2;
    }

    public String getLinkurl2() {
        return linkurl2;
    }

    public void setLinkurl2(String linkurl2) {
        this.linkurl2 = linkurl2;
    }

    public String getLinkTitle3() {
        return linkTitle3;
    }

    public void setLinkTitle3(String linkTitle3) {
        this.linkTitle3 = linkTitle3;
    }

    public String getLinkurl3() {
        return linkurl3;
    }

    public void setLinkurl3(String linkurl3) {
        this.linkurl3 = linkurl3;
    }

    public String getCpId() {
        return cpId;
    }

    public void setCpId(String cpId) {
        this.cpId = cpId;
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

    public String getProgramposterimg2() {
        return programposterimg2;
    }

    public void setProgramposterimg2(String programposterimg2) {
        this.programposterimg2 = programposterimg2;
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

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getPlatformIsUse() {
        return platformIsUse;
    }

    public void setPlatformIsUse(String platformIsUse) {
        this.platformIsUse = platformIsUse;
    }
}
