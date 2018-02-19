package kr.mubeat.cms.domain.manager.admin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by moonkyu.lee on 2017. 7. 13..
 */
@Entity
@Table(name = "tbl_admin_secret")
public class AdminSecret implements Serializable {

    private static final long serialVersionUID = 405600463534259278L;

    @Id
    @Column(name = "secretkey")
    private String secretKey;

    public String getSecretKey() {
        return secretKey;
    }
}
