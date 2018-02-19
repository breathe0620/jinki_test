package kr.mubeat.cms.domain.meta.clip;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by moonkyu.lee on 2017. 9. 4..
 */
@Entity
@Table(name = "tbl_clip_acquisition")
public class ClipAcquisition implements Serializable {

    private static final long serialVersionUID = -4771088178956786086L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "clipmetaid", updatable = false, nullable = false)
    private Long clipMetaId;

    @Column(name = "providertype")
    private String providerType;

    @Column(name = "originclipid")
    private String originClipId;

    @Column(name = "uploadyn")
    private String uploadYn;

    @Column(name = "transcodeyn")
    private String transcodeYn;

    @Column(name = "origins3loc")
    private String originS3Location;

    @Column(name = "acquisitiontime")
    private Integer acquisitionTime;

    @Column(name = "uploadtime")
    private Integer uploadTime;

    @Column(name = "transcodetime")
    private Integer transcodeTime;

    @Column(name = "createdt", insertable = false, updatable = false)
    private Date createDt;

    @Column(name = "modifydt", insertable = false, updatable = false)
    private Date modifyDt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clipmetaid", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private ClipMeta clipMeta;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "originclipid", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private Clip clip;

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

    public String getOriginClipId() {
        return originClipId;
    }

    public void setOriginClipId(String originClipId) {
        this.originClipId = originClipId;
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

    public String getOriginS3Location() {
        return originS3Location;
    }

    public void setOriginS3Location(String originS3Location) {
        this.originS3Location = originS3Location;
    }

    public Integer getAcquisitionTime() {
        return acquisitionTime;
    }

    public void setAcquisitionTime(Integer acquisitionTime) {
        this.acquisitionTime = acquisitionTime;
    }

    public Integer getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Integer uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Integer getTranscodeTime() {
        return transcodeTime;
    }

    public void setTranscodeTime(Integer transcodeTime) {
        this.transcodeTime = transcodeTime;
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

    public ClipMeta getClipMeta() {
        return clipMeta;
    }

    public void setClipMeta(ClipMeta clipMeta) {
        this.clipMeta = clipMeta;
    }

    public Clip getClip() {
        return clip;
    }

    public void setClip(Clip clip) {
        this.clip = clip;
    }
}
