package kr.mubeat.cms.dto.meta.song;

import com.querydsl.core.annotations.QueryProjection;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by moonkyu.lee on 2017. 8. 11..
 */
public class SongExcelDTO implements Serializable {

    private static final long serialVersionUID = 3737836477827973282L;

    private Long songId;
    private Long originId;
    private String songName;
    private String songNameEn;
    private String albumTitle;
    private String artistName;
    private Date createDt;
    private String deleteYn;

    @QueryProjection
    public SongExcelDTO(
            Long songId,
            Long originId,
            String songName,
            String songNameEn,
            String albumTitle,
            String artistName,
            Date createDt,
            String deleteYn
    ) {
        this.songId = songId;
        this.originId = originId;
        this.songName = songName;
        this.songNameEn = songNameEn;
        this.albumTitle = albumTitle;
        this.artistName = artistName;
        this.createDt = createDt;
        this.deleteYn = deleteYn;
    }

    public Long getSongId() {
        return songId;
    }

    public void setSongId(Long songId) {
        this.songId = songId;
    }

    public Long getOriginId() {
        return originId;
    }

    public void setOriginId(Long originId) {
        this.originId = originId;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSongNameEn() {
        return songNameEn;
    }

    public void setSongNameEn(String songNameEn) {
        this.songNameEn = songNameEn;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
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
