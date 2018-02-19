package kr.mubeat.cms.domain.meta.album;

import kr.mubeat.cms.domain.complexkey.AlbumLangKey;

import javax.persistence.*;

/**
 * Created by moonkyu.lee on 2017. 9. 7..
 */
@Entity
@IdClass(AlbumLangKey.class)
@Table(name = "tbl_album_lang")
public class AlbumLang {

    @Id
    @Column(name = "albumid", updatable = false, nullable = false)
    private Long albumId;

    @Id
    @Column(name = "langtype")
    private String langType;

    @Column(name = "albumtitle")
    private String albumTitle;

    public AlbumLang() {
    }

    public AlbumLang(
            Long albumId,
            String langType,
            String albumTitle
    ) {
        this.albumId = albumId;
        this.langType = langType;
        this.albumTitle = albumTitle;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public String getLangType() {
        return langType;
    }

    public void setLangType(String langType) {
        this.langType = langType;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }
}
