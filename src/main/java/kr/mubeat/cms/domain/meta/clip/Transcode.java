package kr.mubeat.cms.domain.meta.clip;

import com.querydsl.core.annotations.QueryProjection;

import java.io.Serializable;

/**
 * Created by moonkyu.lee on 2017. 6. 30..
 */
public class Transcode implements Serializable {

    private static final long serialVersionUID = -3516803408895702549L;

    @QueryProjection
    public Transcode(
            Long clipMetaId,
            String clipCategory,
            String uploadYn
    ) {
        this.clipMetaId = clipMetaId;
        this.clipCategory = clipCategory;
        this.uploadYn = uploadYn;
    }

    private Long clipMetaId;
    private String clipCategory;
    private String uploadYn;

    public Long getClipMetaId() {
        return clipMetaId;
    }

    public void setClipMetaId(Long clipMetaId) {
        this.clipMetaId = clipMetaId;
    }

    public String getClipCategory() {
        return clipCategory;
    }

    public void setClipCategory(String clipCategory) {
        this.clipCategory = clipCategory;
    }

    public String getUploadYn() {
        return uploadYn;
    }

    public void setUploadYn(String uploadYn) {
        this.uploadYn = uploadYn;
    }
}
