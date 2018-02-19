package kr.mubeat.cms.dto.meta.clip;

import com.querydsl.core.annotations.QueryProjection;

import java.io.Serializable;

/**
 * Created by moonkyu.lee on 2017. 8. 11..
 */
public class ClipEsDTO implements Serializable {

    private static final long serialVersionUID = 2608450807219575723L;

    private Long clipMetaId;
    private String clipTitle;
    private String clipTitleEn;
    private String artists;
    private String artistsEn;
    private String songs;
    private String songsEn;

    @QueryProjection
    public ClipEsDTO(
            Long clipMetaId,
            String clipTitle,
            String clipTitleEn,
            String artists,
            String artistsEn,
            String songs,
            String songsEn
    ) {
        this.clipMetaId = clipMetaId;
        this.clipTitle = clipTitle;
        this.clipTitleEn = clipTitleEn;
        this.artists = artists;
        this.artistsEn = artistsEn;
        this.songs = songs;
        this.songsEn = songsEn;
    }

    public Long getClipMetaId() {
        return clipMetaId;
    }

    public void setClipMetaId(Long clipMetaId) {
        this.clipMetaId = clipMetaId;
    }

    public String getClipTitle() {
        return clipTitle;
    }

    public void setClipTitle(String clipTitle) {
        this.clipTitle = clipTitle;
    }

    public String getClipTitleEn() {
        return clipTitleEn;
    }

    public void setClipTitleEn(String clipTitleEn) {
        this.clipTitleEn = clipTitleEn;
    }

    public String getArtists() {
        return artists;
    }

    public void setArtists(String artists) {
        this.artists = artists;
    }

    public String getArtistsEn() {
        return artistsEn;
    }

    public void setArtistsEn(String artistsEn) {
        this.artistsEn = artistsEn;
    }

    public String getSongs() {
        return songs;
    }

    public void setSongs(String songs) {
        this.songs = songs;
    }

    public String getSongsEn() {
        return songsEn;
    }

    public void setSongsEn(String songsEn) {
        this.songsEn = songsEn;
    }
}
