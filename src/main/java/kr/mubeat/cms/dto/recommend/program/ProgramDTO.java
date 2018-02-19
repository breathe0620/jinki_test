package kr.mubeat.cms.dto.recommend.program;

import com.querydsl.core.annotations.QueryProjection;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by moonkyu.lee on 2017. 11. 9..
 */
public class ProgramDTO implements Serializable {

    private static final long serialVersionUID = -7088690320366020539L;

    private Integer recommendProgramId;
    private String programId;
    private String programTitle;
    private String programTitleEn;
    private String bannerImgUrl;
    private Date modifyDate;

    @QueryProjection
    public ProgramDTO(
            Integer recommendProgramId,
            String programId,
            String programTitle,
            String programTitleEn,
            Date modifyDate
    ) {
        this.recommendProgramId = recommendProgramId;
        this.programId = programId;
        this.programTitle = programTitle;
        this.programTitleEn = programTitleEn;
        this.modifyDate = modifyDate;
    }

    @QueryProjection
    public ProgramDTO(
        Integer recommendProgramId,
        String programId,
        String programTitle,
        String programTitleEn,
        String bannerImgUrl,
        Date modifyDate
    ) {
        this.recommendProgramId = recommendProgramId;
        this.programId = programId;
        this.programTitle = programTitle;
        this.programTitleEn = programTitleEn;
        this.bannerImgUrl = bannerImgUrl;
        this.modifyDate = modifyDate;
    }

    public ProgramDTO() {
    }

    public Integer getRecommendProgramId() {
        return recommendProgramId;
    }

    public void setRecommendProgramId(Integer recommendProgramId) {
        this.recommendProgramId = recommendProgramId;
    }

    public String getProgramId() {
        return programId;
    }

    public void setProgramId(String programId) {
        this.programId = programId;
    }

    public String getProgramTitle() {
        return programTitle;
    }

    public void setProgramTitle(String programTitle) {
        this.programTitle = programTitle;
    }

    public String getProgramTitleEn() {
        return programTitleEn;
    }

    public void setProgramTitleEn(String programTitleEn) {
        this.programTitleEn = programTitleEn;
    }

    public String getBannerImgUrl() {
        return bannerImgUrl;
    }

    public void setBannerImgUrl(String bannerImgUrl) {
        this.bannerImgUrl = bannerImgUrl;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }
}
