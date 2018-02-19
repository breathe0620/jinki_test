package kr.mubeat.cms.dto.meta.acquisition;

import com.querydsl.core.annotations.QueryProjection;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by moonkyu.lee on 2017. 9. 4..
 */
public class ClipAcquisitionDTO implements Serializable {

    private static final long serialVersionUID = 6664430846442298926L;

    private Long clipMetaId;
    private String providerType;
    private String originalClipId;
    private String uploadYn;
    private String transcodeYn;
    private String originalS3Location;
    private Date createDt;

    public ClipAcquisitionDTO() {
    }

    @QueryProjection
    public ClipAcquisitionDTO(
        Long clipMetaId,
        String providerType,
        String originalClipId,
        String uploadYn,
        String transcodeYn,
        String originalS3Location,
        Date createDt
    ) {
        this.clipMetaId = clipMetaId;
        this.providerType = providerType;
        this.originalClipId = originalClipId;
        this.uploadYn = uploadYn;
        this.transcodeYn = transcodeYn;
        this.originalS3Location = originalS3Location;
        this.createDt = createDt;
    }

    public Long getClipMetaId() {
        return clipMetaId;
    }

    public void setClipMetaId(Long clipMetaId) {
        this.clipMetaId = clipMetaId;
    }

    public String getProviderType() {
        return providerType;
    }

    public void setProviderType(String providerType) {
        this.providerType = providerType;
    }

    public String getOriginalClipId() {
        return originalClipId;
    }

    public void setOriginalClipId(String originalClipId) {
        this.originalClipId = originalClipId;
    }

    public String getUploadYn() {
        return uploadYn;
    }

    public void setUploadYn(String uploadYn) {
        this.uploadYn = uploadYn;
    }

    public String getTranscodeYn() {
        return transcodeYn;
    }

    public void setTranscodeYn(String transcodeYn) {
        this.transcodeYn = transcodeYn;
    }

    public String getOriginalS3Location() {
        return originalS3Location;
    }

    public void setOriginalS3Location(String originalS3Location) {
        this.originalS3Location = originalS3Location;
    }

    public Date getCreateDt() {
        return createDt;
    }

    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
    }
}
