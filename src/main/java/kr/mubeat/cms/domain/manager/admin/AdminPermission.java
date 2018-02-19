package kr.mubeat.cms.domain.manager.admin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by doohwan.yoo on 2017. 5. 26..
 */
@Entity
@Table(name = "tbl_admin_permission")
public class AdminPermission {

    @Id
    @Column(name="adminno", nullable = false)
    private Long adminNo;

    @Column(name="meta", nullable = false)
    private String meta;

    @Column(name="member", nullable = false)
    private String member;

    public Long getAdminNo() {
        return adminNo;
    }

    public void setAdminNo(Long adminNo) {
        this.adminNo = adminNo;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }
}
