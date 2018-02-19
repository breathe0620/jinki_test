package kr.mubeat.cms.domain.meta.clip;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Created by doohwan.yoo on 2017. 4. 27..
 */
@MappedSuperclass
class BaseInfo {

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "originid", nullable = false)
    private String originId;

    @Column(name = "targetage")
    private Integer targetAge;

    @Column(name = "searchkeyword")
    private String searchKeyword;

    @Column(name = "regdate", nullable = false)
    private String regDate;

    @Column(name = "modifydate", nullable = false)
    private String modfyDate;

    @Column(name = "targetnation")
    private String targetNation;

    @Column(name = "limitnation")
    private String limitNation;

    @Column(name = "targetplatform")
    private String targetPlatform;

    @Column(name = "isuse", nullable = false)
    private String isUse;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginId() {
        return originId;
    }

    public void setOriginId(String originId) {
        this.originId = originId;
    }

    public Integer getTargetAge() {
        return targetAge;
    }

    public void setTargetAge(Integer targetAge) {
        this.targetAge = targetAge;
    }

    public String getSearchKeyword() {
        return searchKeyword;
    }

    public void setSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getModfyDate() {
        return modfyDate;
    }

    public void setModfyDate(String modfyDate) {
        this.modfyDate = modfyDate;
    }

    public String getTargetNation() {
        return targetNation;
    }

    public void setTargetNation(String targetNation) {
        this.targetNation = targetNation;
    }

    public String getLimitNation() {
        return limitNation;
    }

    public void setLimitNation(String limitNation) {
        this.limitNation = limitNation;
    }

    public String getTargetPlatform() {
        return targetPlatform;
    }

    public void setTargetPlatform(String targetPlatform) {
        this.targetPlatform = targetPlatform;
    }

    public String getIsUse() {
        return isUse;
    }

    public void setIsUse(String isUse) {
        this.isUse = isUse;
    }
}
