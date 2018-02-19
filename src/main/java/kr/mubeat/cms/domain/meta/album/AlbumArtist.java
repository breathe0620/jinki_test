package kr.mubeat.cms.domain.meta.album;

import com.querydsl.core.annotations.QueryProjection;
import kr.mubeat.cms.domain.complexkey.AlbumArtistKey;
import kr.mubeat.cms.domain.meta.artist.Artist;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by moonkyu.lee on 2017. 7. 18..
 */
@Entity
@IdClass(AlbumArtistKey.class)
@Table(name = "tbl_album_artist")
public class AlbumArtist implements Serializable {

    private static final long serialVersionUID = -8497829160112294730L;

    @QueryProjection
    public AlbumArtist(
            Long albumId,
            Long artistId
    ) {
        this.albumId = albumId;
        this.artistId = artistId;
    }

    public AlbumArtist() {}

    @Id
    @Column(name = "albumid", updatable = false, nullable = false)
    private Long albumId;

    @Id
    @Column(name = "artistid")
    private Long artistId;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "artistid", updatable = false, insertable = false)
    private Artist artist;

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
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
