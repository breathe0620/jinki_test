package kr.mubeat.cms.domain.manager.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by moonkyu.lee on 2017. 8. 21..
 */
@Entity
@Table(name = "tbl_user")
@JsonSerialize
public class User implements Serializable {

    private static final long serialVersionUID = -9054027323905735119L;

    @Id
    @Column(name = "userno", updatable = false, nullable = false)
    private Long userNo;

    @Column(name = "userid", updatable = false, nullable = false)
    private String userId;

    @Column(name = "usertype", nullable = false)
    private Integer userType;

    @Column(name = "usersecret", nullable = false)
    private String userSecret;

    @Column(name = "userstatus", nullable = false)
    private String userStatus;

    @Column(name = "username", nullable = false)
    private String userName;

    @Column(name = "useremail")
    private String userEmail;

    @Column(name = "createdt", nullable = false, insertable = false, updatable = false)
    private Date createDt;

    @Column(name = "modifydt", nullable = false, insertable = false)
    private Date modifyDt;

    @Column(name = "lastlogindt", nullable = false)
    private Date lastLoginDt;

    public Long getUserNo() {
        return userNo;
    }

    public void setUserNo(Long userNo) {
        this.userNo = userNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    @JsonIgnore
    public String getUserSecret() {
        return userSecret;
    }

    public void setUserSecret(String userSecret) {
        this.userSecret = userSecret;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
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

    public Date getLastLoginDt() {
        return lastLoginDt;
    }

    public void setLastLoginDt(Date lastLoginDt) {
        this.lastLoginDt = lastLoginDt;
    }
}
