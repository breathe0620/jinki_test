package kr.mubeat.cms.domain.meta.artist;

import kr.mubeat.cms.domain.complexkey.ArtistLangKey;

import javax.persistence.*;

/**
 * Created by moonkyu.lee on 2017. 9. 7..
 */
@Entity
@IdClass(ArtistLangKey.class)
@Table(name = "tbl_artist_lang")
public class ArtistLang {

    @Id
    @Column(name = "artistid", updatable = false, nullable = false)
    private Long artistId;

    @Id
    @Column(name = "langtype")
    private String langType;

    @Column(name = "artistname")
    private String artistName;

    public ArtistLang() {
    }

    public ArtistLang(
            Long artistId,
            String langType,
            String artistName
    ) {
        this.artistId = artistId;
        this.langType = langType;
        this.artistName = artistName;
    }

    public Long getArtistId() {
        return artistId;
    }

    public void setArtistId(Long artistId) {
        this.artistId = artistId;
    }

    public String getLangType() {
        return langType;
    }

    public void setLangType(String langType) {
        this.langType = langType;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }
}
