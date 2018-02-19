package kr.mubeat.cms.dto.meta.program;

import com.querydsl.core.annotations.QueryProjection;

import java.io.Serializable;

/**
 * Created by moonkyu.lee on 2017. 9. 21..
 */
public class ProgramMetaDTO implements Serializable {

    private static final long serialVersionUID = -6022034693369948301L;

    private String programId;
    private String corporator;
    private String programTitle;
    private String programTitleEn;
    private String limitnation;

    @QueryProjection
    public ProgramMetaDTO(
        String programId,
        String corporator,
        String programTitle,
        String programTitleEn,
        String limitnation
    ) {
        this.programId = programId;
        this.corporator = corporator;
        this.programTitle = programTitle;
        this.programTitleEn = programTitleEn;
        this.limitnation = limitnation;
    }

    public ProgramMetaDTO() {
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

    public String getLimitnation() {
        return limitnation;
    }

    public void setLimitnation(String limitnation) {
        this.limitnation = limitnation;
    }
}
