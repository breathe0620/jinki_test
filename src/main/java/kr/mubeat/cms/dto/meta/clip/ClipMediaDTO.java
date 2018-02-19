package kr.mubeat.cms.dto.meta.clip;

import com.querydsl.core.annotations.QueryProjection;

import java.io.Serializable;

/**
 * Created by moonkyu.lee on 2017. 6. 26..
 */
public class ClipMediaDTO implements Serializable {

    private static final long serialVersionUID = -1878616741025073710L;

    @QueryProjection
    public ClipMediaDTO(
        Long clipMetaId,
        Integer mediaType,
        String mediaFullPath
    ) {
        this.clipMetaId = clipMetaId;
        this.mediaType = mediaType;
        this.mediaFullPath = mediaFullPath;
    }

    private long clipMetaId;
    private Integer mediaType;
    private String mediaFullPath;

    public long getClipMetaId() {
        return clipMetaId;
    }

    public void setClipMetaId(long clipMetaId) {
        this.clipMetaId = clipMetaId;
    }

    public Integer getMediaType() {
        return mediaType;
    }

    public void setMediaType(Integer mediaType) {
        this.mediaType = mediaType;
    }

    public String getMediaFullPath() {
        return mediaFullPath;
    }

    public void setMediaFullPath(String mediaFullPath) {
        this.mediaFullPath = mediaFullPath;
    }
}
