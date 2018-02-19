package kr.mubeat.cms.domain.meta.artist;

import com.querydsl.core.annotations.QueryProjection;
import kr.mubeat.cms.domain.complexkey.MemberArtistKey;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by doohwan.yoo on 2017. 6. 5..
 */
@Entity
@IdClass(MemberArtistKey.class)
@Table(name = "tbl_member_artist")
public class MemberArtist implements Serializable {

    private static final long serialVersionUID = 4738157741784413401L;

    public MemberArtist(Long artistId, Long memberArtistId) {
        this.artistId = artistId;
        this.memberArtistId = memberArtistId;
    }

    @QueryProjection
    public MemberArtist(Long artistId, Long memberArtistId, String memberArtistName) {
        this.artistId = artistId;
        this.memberArtistId = memberArtistId;
        this.memberArtistName = memberArtistName;
    }

    public MemberArtist(){}

    @Id
    @Column(name = "artistid", updatable = false, nullable = false)
    @ApiModelProperty(hidden = true)
    private Long artistId;

    @Id
    @Column(name = "memberartistid", nullable = false)
    private Long memberArtistId;

    @ApiModelProperty(hidden = true)
    @Transient
    private String memberArtistName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberartistid", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    @ApiModelProperty(hidden = true)
    private Artist artist;

    public Long getArtistId() {
        return artistId;
    }

    public void setArtistId(Long artistId) {
        this.artistId = artistId;
    }

    public Long getMemberArtistId() {
        return memberArtistId;
    }

    public void setMemberArtistId(Long memberArtistId) {
        this.memberArtistId = memberArtistId;
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
