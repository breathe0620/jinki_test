package kr.mubeat.cms.domain.meta.artist;

import com.querydsl.core.annotations.QueryProjection;
import kr.mubeat.cms.domain.complexkey.RelativeArtistKey;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by doohwan.yoo on 2017. 6. 5..
 */
@Entity
@IdClass(RelativeArtistKey.class)
@Table(name = "tbl_relative_artist")
public class RelativeArtist implements Serializable {


    private static final long serialVersionUID = -3030702250012199431L;

    public RelativeArtist(Long artistId, Long relativeArtistId) {
        this.artistId = artistId;
        this.relativeArtistId = relativeArtistId;
    }

    @QueryProjection
    public RelativeArtist(Long artistId, Long relativeArtistId, String memberArtistName) {
        this.artistId = artistId;
        this.relativeArtistId = relativeArtistId;
        this.memberArtistName = memberArtistName;
    }

    public RelativeArtist(){};

    @Id
    @Column(name = "artistid", updatable = false, nullable = false)
    @ApiModelProperty(hidden = true)
    private Long artistId;

    @Id
    @Column(name = "relativeartistid", nullable = false)
    private Long relativeArtistId;

    @ApiModelProperty(hidden = true)
    @Transient
    private String memberArtistName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "relativeartistid", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    @ApiModelProperty(hidden = true)
    private Artist artist;

    public Long getArtistId() {
        return artistId;
    }

    public void setArtistId(Long artistId) {
        this.artistId = artistId;
    }

    public Long getRelativeArtistId() {
        return relativeArtistId;
    }

    public void setRelativeArtistId(Long relativeArtistId) {
        this.relativeArtistId = relativeArtistId;
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
