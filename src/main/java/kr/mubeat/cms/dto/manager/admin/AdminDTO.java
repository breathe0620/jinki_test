package kr.mubeat.cms.dto.manager.admin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.querydsl.core.annotations.QueryProjection;
import kr.mubeat.cms.domain.manager.admin.AdminPermission;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by doohwan.yoo on 2017. 4. 18..
 */
@JsonSerialize
public class AdminDTO implements Serializable {

    private static final long serialVersionUID = 5993924724100145172L;

    @QueryProjection
    public AdminDTO(
            Long adminNo,
            String adminName,
            String adminId,
            String phoneNum,
            String adminType,
            Date createDt
    ) {
        this.adminNo = adminNo;
        this.adminId = adminId;
        this.adminName = adminName;
        this.phoneNum = phoneNum;
        this.adminType = adminType;
        this.createDt = createDt;
    }

    public AdminDTO(){}

    private Long adminNo;
    private String adminName;
    private String adminId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String adminPwd;
    private String phoneNum;
    private String adminType;
    private Date createDt;

    private String secretKey;

    private AdminPermission adminPermission;

    public Long getAdminNo() {
        return adminNo;
    }

    public void setAdminNo(Long adminNo) {
        this.adminNo = adminNo;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    @JsonIgnore
    public String getAdminPwd() {
        return adminPwd;
    }

    public void setAdminPwd(String adminPwd) {
        this.adminPwd = adminPwd;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getAdminType() {
        return adminType;
    }

    public void setAdminType(String adminType) {
        this.adminType = adminType;
    }

    public Date getCreateDt() {
        return createDt;
    }

    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public AdminPermission getAdminPermission() {
        return adminPermission;
    }

    public void setAdminPermission(AdminPermission adminPermission) {
        this.adminPermission = adminPermission;
    }
}
