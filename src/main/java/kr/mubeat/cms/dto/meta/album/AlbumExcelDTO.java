package kr.mubeat.cms.dto.meta.album;

import com.querydsl.core.annotations.QueryProjection;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by moonkyu.lee on 2017. 8. 11..
 */
public class AlbumExcelDTO implements Serializable {

    private static final long serialVersionUID = 7026606202958865558L;

    private Long albumId;
    private Long originId;
    private String artistName;
    private String albumTitle;
    private String albumTitleEn;
    private Date publishDt;
    private Date createDt;
    private String deleteYn;

    @QueryProjection
    public AlbumExcelDTO(
            Long albumId,
            Long originId,
            String artistName,
            String albumTitle,
            String albumTitleEn,
            Date publishDt,
            Date createDt,
            String deleteYn
    ) {
        this.albumId = albumId;
        this.originId = originId;
        this.artistName = artistName;
        this.albumTitle = albumTitle;
        this.albumTitleEn = albumTitleEn;
        this.publishDt = publishDt;
        this.createDt = createDt;
        this.deleteYn = deleteYn;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public Long getOriginId() {
        return originId;
    }

    public void setOriginId(Long originId) {
        this.originId = originId;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    public String getAlbumTitleEn() {
        return albumTitleEn;
    }

    public void setAlbumTitleEn(String albumTitleEn) {
        this.albumTitleEn = albumTitleEn;
    }

    public Date getPublishDt() {
        return publishDt;
    }

    public void setPublishDt(Date publishDt) {
        this.publishDt = publishDt;
    }

    public Date getCreateDt() {
        return createDt;
    }

    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
    }

    public String getDeleteYn() {
        return deleteYn;
    }

    public void setDeleteYn(String deleteYn) {
        this.deleteYn = deleteYn;
    }
}
