package kr.mubeat.cms.domain.meta.clip;

import com.querydsl.core.annotations.QueryProjection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by moonkyu.lee on 2017. 6. 30..
 */
@Entity
@Table(name = "tbl_clip_media")
public class ClipMedia implements Serializable {

    private static final long serialVersionUID = -2653225579681408767L;

    @QueryProjection
    public ClipMedia(
            Long clipMetaId,
            Integer mediaType,
            String mediaPath,
            String baseUrl
    ) {
        this.clipMetaId = clipMetaId;
        this.mediaType = mediaType;
        this.mediaPath = mediaPath;
        this.baseUrl = baseUrl;
    }

    public ClipMedia() {}

    @Id
    @Column(name = "clipmetaid", updatable = false, nullable = false)
    private Long clipMetaId;

    @Column(name = "mediatype")
    private Integer mediaType;

    @Column(name = "mediapath")
    private String mediaPath;

    @Column(name = "baseurl")
    private String baseUrl;

    public Long getClipMetaId() {
        return clipMetaId;
    }

    public void setClipMetaId(Long clipMetaId) {
        this.clipMetaId = clipMetaId;
    }

    public Integer getMediaType() {
        return mediaType;
    }

    public void setMediaType(Integer mediaType) {
        this.mediaType = mediaType;
    }

    public String getMediaPath() {
        return mediaPath;
    }

    public void setMediaPath(String mediaPath) {
        this.mediaPath = mediaPath;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}
