package kr.mubeat.cms.domain.meta.clip;

import com.querydsl.core.annotations.QueryProjection;
import kr.mubeat.cms.domain.complexkey.ClipArtistKey;
import kr.mubeat.cms.domain.meta.artist.Artist;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by moonkyu.lee on 2017. 6. 20..
 */
@Entity
@IdClass(ClipArtistKey.class)
@Table(name = "tbl_clip_artist")
public class ClipArtist implements Serializable {

    private static final long serialVersionUID = 4076997872085501827L;

    @QueryProjection
    public ClipArtist(
            Long clipMetaId,
            Long artistId,
            Long originId,
            String artistName,
            String artistnameEn,
            String artistMainImgUrl,
            String artistProfileImgUrl
    ) {
        this.clipMetaId = clipMetaId;
        this.artistId = artistId;
        this.originId = originId;
        this.artistName = artistName;
        this.artistNameEn = artistnameEn;
        this.artistMainImgUrl = artistMainImgUrl;
        this.artistProfileImgUrl = artistProfileImgUrl;
    }

    public ClipArtist(){}

    @Id
    @Column(name = "clipmetaid", updatable = false, nullable = false)
    private Long clipMetaId;

    @Id
    @Column(name = "artistid")
    private Long artistId;

    @Transient
    private Long originId;
    @Transient
    private String artistName;
    @Transient
    private String artistNameEn;
    @Transient
    private String artistMainImgUrl;
    @Transient
    private String artistProfileImgUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artistid", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private Artist artist;

    public Long getClipMetaId() {
        return clipMetaId;
    }

    public void setClipMetaId(Long clipMetaId) {
        this.clipMetaId = clipMetaId;
    }

    public Long getArtistId() {
        return artistId;
    }

    public void setArtistId(Long artistId) {
        this.artistId = artistId;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
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

    public String getArtistNameEn() {
        return artistNameEn;
    }

    public void setArtistNameEn(String artistNameEn) {
        this.artistNameEn = artistNameEn;
    }

    public String getArtistMainImgUrl() {
        return artistMainImgUrl;
    }

    public void setArtistMainImgUrl(String artistMainImgUrl) {
        this.artistMainImgUrl = artistMainImgUrl;
    }

    public String getArtistProfileImgUrl() {
        return artistProfileImgUrl;
    }

    public void setArtistProfileImgUrl(String artistProfileImgUrl) {
        this.artistProfileImgUrl = artistProfileImgUrl;
    }
}
