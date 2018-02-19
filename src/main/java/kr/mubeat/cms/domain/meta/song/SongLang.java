package kr.mubeat.cms.domain.meta.song;

import kr.mubeat.cms.domain.complexkey.SongLangKey;

import javax.persistence.*;

/**
 * Created by moonkyu.lee on 2017. 9. 7..
 */
@Entity
@IdClass(SongLangKey.class)
@Table(name = "tbl_song_lang")
public class SongLang {

    @Id
    @Column(name = "songid", updatable = false, nullable = false)
    private Long songId;

    @Id
    @Column(name = "langtype")
    private String langType;

    @Column(name = "songname")
    private String songName;

    public SongLang() {
    }

    public SongLang(
            Long songId,
            String langType,
            String songName
    ) {
        this.songId = songId;
        this.langType = langType;
        this.songName = songName;
    }

    public Long getSongId() {
        return songId;
    }

    public void setSongId(Long songId) {
        this.songId = songId;
    }

    public String getLangType() {
        return langType;
    }

    public void setLangType(String langType) {
        this.langType = langType;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }
}
