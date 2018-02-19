package kr.mubeat.cms.domain.meta.clip;

import com.querydsl.core.annotations.QueryProjection;
import kr.mubeat.cms.domain.complexkey.ClipSongKey;
import kr.mubeat.cms.domain.meta.song.Song;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by moonkyu.lee on 2017. 6. 26..
 */
@Entity
@IdClass(ClipSongKey.class)
@Table(name = "tbl_clip_song")
public class ClipSong implements Serializable {

    private static final long serialVersionUID = -2202740748499031786L;

    @QueryProjection
    public ClipSong(
            Long clipMetaId,
            Long songId,
            Long originId,
            String songName,
            String songNameEn,
            Date publishDt,
            Integer genreId,
            String albumImgUrl
    ) {
        this.clipMetaId = clipMetaId;
        this.songId = songId;
        this.originId = originId;
        this.songName = songName;
        this.songNameEn = songNameEn;
        this.publishDt = publishDt;
        this.genreId = genreId;
        this.albumImgUrl = albumImgUrl;
    }

    public ClipSong(){}

    @Id
    @Column(name = "clipmetaid", updatable = false, nullable = false)
    private Long clipMetaId;

    @Id
    @Column(name = "songid", nullable = false)
    private Long songId;

    @Transient
    private Long originId;
    @Transient
    private String songName;
    @Transient
    private String songNameEn;
    @Transient
    private Date publishDt;
    @Transient
    private Integer genreId;
    @Transient
    private String albumImgUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "songid", updatable = false, insertable = false)
    private Song song;

    public Long getClipMetaId() {
        return clipMetaId;
    }

    public void setClipMetaId(Long clipMetaId) {
        this.clipMetaId = clipMetaId;
    }

    public Long getSongId() {
        return songId;
    }

    public void setSongId(Long songId) {
        this.songId = songId;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
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

    public Date getPublishDt() {
        return publishDt;
    }

    public void setPublishDt(Date publishDt) {
        this.publishDt = publishDt;
    }

    public Integer getGenreId() {
        return genreId;
    }

    public void setGenreId(Integer genreId) {
        this.genreId = genreId;
    }

    public String getAlbumImgUrl() {
        return albumImgUrl;
    }

    public void setAlbumImgUrl(String albumImgUrl) {
        this.albumImgUrl = albumImgUrl;
    }
}
