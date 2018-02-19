package kr.mubeat.cms.dto.meta.acquisition;

import com.querydsl.core.annotations.QueryProjection;

import java.io.Serializable;

/**
 * Created by moonkyu.lee on 2017. 6. 23..
 */
public class ProgramSearchDTO implements Serializable {

    private String programId;
    private String programTitle;

    @QueryProjection
    public ProgramSearchDTO(String programId, String programTitle) {
        this.programId = programId;
        this.programTitle = programTitle;
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
}
