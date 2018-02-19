package kr.mubeat.cms.domain.meta.artist;

import com.querydsl.core.annotations.QueryProjection;
import kr.mubeat.cms.domain.complexkey.SimilarArtistKey;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by doohwan.yoo on 2017. 6. 5..
 */
@Entity
@IdClass(SimilarArtistKey.class)
@Table(name = "tbl_similar_artist")
public class SimilarArtist implements Serializable {

    private static final long serialVersionUID = -2047579487329046278L;

    public SimilarArtist(Long artistId, Long similarArtistId) {
        this.artistId = artistId;
        this.similarArtistId = similarArtistId;
    }

    @QueryProjection
    public SimilarArtist(Long artistId, Long similarArtistId, String memberArtistName) {
        this.artistId = artistId;
        this.similarArtistId = similarArtistId;
        this.memberArtistName = memberArtistName;
    }

    public SimilarArtist(){}

    @Id
    @Column(name = "artistid", updatable = false, nullable = false)
    @ApiModelProperty(hidden = true)
    private Long artistId;

    @Id
    @Column(name = "similarartistid", nullable = false)
    private Long similarArtistId;

    @ApiModelProperty(hidden = true)
    @Transient
    private String memberArtistName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "similarartistid", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    @ApiModelProperty(hidden = true)
    private Artist artist;

    public Long getArtistId() {
        return artistId;
    }

    public void setArtistId(Long artistId) {
        this.artistId = artistId;
    }

    public Long getSimilarArtistId() {
        return similarArtistId;
    }

    public void setSimilarArtistId(Long similarArtistId) {
        this.similarArtistId = similarArtistId;
    }

    @Transient
    public String getMemberArtistName() {
        return memberArtistName;
    }

    public void setMemberArtistName(String memberArtistName) {
        this.memberArtistName = memberArtistName;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }
}
