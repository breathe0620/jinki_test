package kr.mubeat.cms.dto.manager.version;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.querydsl.core.annotations.QueryProjection;

import java.io.Serializable;

/**
 * Created by moonkyu.lee on 2017. 7. 14..
 */
@JsonSerialize
public class VersionDTO implements Serializable {

    private static final long serialVersionUID = 6969627351780753998L;

    @QueryProjection
    public VersionDTO(
            String osCode,
            String osName,
            String version
    ) {
        this.osCode = osCode;
        this.osName = osName;
        this.version = version;
    }

    private String osCode;
    private String osName;
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
