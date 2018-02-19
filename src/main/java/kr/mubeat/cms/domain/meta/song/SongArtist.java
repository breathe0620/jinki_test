package kr.mubeat.cms.domain.meta.song;

import com.querydsl.core.annotations.QueryProjection;
import kr.mubeat.cms.domain.complexkey.SongArtistKey;
import kr.mubeat.cms.domain.meta.artist.Artist;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by moonkyu.lee on 2017. 7. 21..
 */
@Entity
@IdClass(SongArtistKey.class)
@Table(name = "tbl_song_artist")
public class SongArtist implements Serializable {

    private static final long serialVersionUID = 1503674382875409715L;

    @QueryProjection
    public SongArtist(
            Long songId,
            Long artistId
    ) {
        this.songId = songId;
        this.artistId = artistId;
    }

    public SongArtist() {}

    @Id
    @Column(name = "songid", updatable = false, nullable = false)
    private Long songId;

    @Id
    @Column(name = "artistid")
    private Long artistId;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "artistid", updatable = false, insertable = false)
    private Artist artist;

    public Long getSongId() {
        return songId;
    }

    public void setSongId(Long songId) {
        this.songId = songId;
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
}
