package kr.mubeat.cms.domain.manager.version;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by moonkyu.lee on 2017. 7. 14..
 */
@Entity
@Table(name = "tbl_app_info")
public class Version implements Serializable {

    private static final long serialVersionUID = -2541027590364490466L;

    @Id
    @Column(name = "oscode")
    private String osCode;

    @Column(name = "osname")
    private String osName;

    @Column(name = "version")
    private String version;

    public String getOsCode() {
        return osCode;
    }

    public void setOsCode(String osCode) {
        this.osCode = osCode;
    }

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
