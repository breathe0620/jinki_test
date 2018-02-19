package kr.mubeat.cms.dto.manager.user;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.querydsl.core.annotations.QueryProjection;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by moonkyu.lee on 2017. 8. 21..
 */
@JsonSerialize
public class UserDTO implements Serializable {

    private static final long serialVersionUID = -4504786583217647261L;


    @QueryProjection
    public UserDTO(
            Long userNo,
            String userId,
            Integer userType,
            String userStatus,
            String userName,
            String userEmail,
            Date createDt,
            Date lastLoginDt
    ) {
        this.userNo = userNo;
        this.userId = userId;
        this.userType = userType;
        this.userStatus = userStatus;
        this.userName = userName;
        this.userEmail = userEmail;
        this.createDt = createDt;
        this.lastLoginDt = lastLoginDt;
    }

    private Long userNo;
    private String userId;
    private Integer userType;
    private String userStatus;
    private String userName;
    private String userEmail;
    private Date createDt;
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

    public Date getLastLoginDt() {
        return lastLoginDt;
    }

    public void setLastLoginDt(Date lastLoginDt) {
        this.lastLoginDt = lastLoginDt;
    }
}
