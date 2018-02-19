package kr.mubeat.cms.domain.meta.agency;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by doohwan.yoo on 2017. 6. 8..
 * Created by moonkyu.lee on 2017. 8. 11..
 */
@Entity
@Table(name = "tbl_agency")
public class Agency implements Serializable {

    private static final long serialVersionUID = -3563600376310410834L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "agencyid", nullable = false, insertable = false, updatable = false)
    @ApiModelProperty(hidden = true)
    private Integer agencyId;

    @Column(name = "agencyname", nullable = false)
    private String agencyName;

    @Column(name = "agencyname_en", nullable = false)
    private String agencyNameEn;

    public Integer getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Integer agencyId) {
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