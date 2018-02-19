package kr.mubeat.cms.dto.meta.agency;

import com.querydsl.core.annotations.QueryProjection;

import java.io.Serializable;

/**
 * Created by moonkyu.lee on 2017. 8. 11..
 */
public class AgencyExcelDTO implements Serializable {

    private static final long serialVersionUID = -6672959430421400540L;

    private Long agencyId;
    private String agencyName;

    @QueryProjection
    public AgencyExcelDTO(
            Long agencyId,
            String agencyName
    ) {
        this.agencyId = agencyId;
        this.agencyName = agencyName;
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
}
