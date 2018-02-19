package kr.mubeat.cms.domain.manager.admin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by doohwan.yoo on 2017. 4. 18..
 * Domain Model Admin
 */
@Entity
@Table(name = "tbl_admin", uniqueConstraints = {@UniqueConstraint(columnNames={"adminid"})})
@JsonSerialize
public class Admin implements Serializable {

    private static final long serialVersionUID = 4806325343907254958L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="adminno", updatable = false, nullable = false)
    private Long adminNo;

    @Column(name="adminid", nullable = false, updatable = false)
    private String adminId;

    @Column(name="adminname", nullable = false)
    private String adminName;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name="adminpwd", nullable = false)
    private String adminPwd;

    @Column(name="admintype", nullable = false)
    private String adminType;

    @Column(name="phonenum", nullable = false)
    private String phoneNum;

    @Column(name="createdt", nullable = false, insertable = false, updatable = false)
    private Date createDt;

    @Column(name="modifydt", nullable = false, insertable = false)
    private Date modifyDt;

    @ApiModelProperty(hidden = true)
    @Transient
    private String secretKey;

    @OneToOne(fetch = FetchType.LAZY, cascade={CascadeType.ALL})
    @JoinColumn(name="adminno", updatable = false, insertable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private AdminPermission adminPermission;

//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//    private String adminPrevPwd;

//    @JsonIgnore
//    public String getAdminPrevPwd() {
//        return adminPrevPwd;
//    }
//
//    public void setAdminPrevPwd(String adminPrevPwd) {
//        this.adminPrevPwd = adminPrevPwd;
//    }

    @JsonIgnore
    public String getAdminPwd() {
        return adminPwd;
    }

    public void setAdminPwd(String adminPwd) {
        this.adminPwd = adminPwd;
    }

    public Long getAdminNo() {
        return adminNo;
    }

    public void setAdminNo(Long adminNo) {
        this.adminNo = adminNo;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminType() {
        return adminType;
    }

    public void setAdminType(String adminType) {
        this.adminType = adminType;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public Date getCreateDt() {
        return createDt;
    }

    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
    }

    public Date getModifyDt() {
        return modifyDt;
    }

    public void setModifyDt(Date modifyDt) {
        this.modifyDt = modifyDt;
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
