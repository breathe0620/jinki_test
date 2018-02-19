package kr.mubeat.cms.dto.meta.agency;

import com.querydsl.core.annotations.QueryProjection;

import java.io.Serializable;

/**
 * Created by moonkyu.lee on 2017. 9. 7..
 */
public class AgencyDTO implements Serializable {

    private static final long serialVersionUID = 5119306632580502481L;

    private Long agencyId;
    private String agencyName;
    private String agencyNameEn;

    @QueryProjection
    public AgencyDTO(
        Long agencyId,
        String agencyName,
        String agencyNameEn
    ) {
        this.agencyId = agencyId;
        this.agencyName = agencyName;
        this.agencyNameEn = agencyNameEn;
    }

    public AgencyDTO() {
    }

    public Long getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Long agencyId) {
        this.agencyId = agencyId;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getAgencyNameEn() {
        return agencyNameEn;
    }

    public void setAgencyNameEn(String agencyNameEn) {
        this.agencyNameEn = agencyNameEn;
    }
}
