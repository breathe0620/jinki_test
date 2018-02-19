package kr.mubeat.cms.domain.meta.artist;

import com.querydsl.core.annotations.QueryProjection;
import kr.mubeat.cms.domain.complexkey.ArtistGroupKey;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by doohwan.yoo on 2017. 6. 8..
 */
@Entity
@IdClass(ArtistGroupKey.class)
@Table(name = "tbl_artist_group")
public class ArtistGroup implements Serializable {

    private static final long serialVersionUID = -2875493198390515694L;

    @QueryProjection
    public ArtistGroup(Long artistId, Long artistGroupId, String groupName) {
        this.artistId = artistId;
        this.artistGroupId = artistGroupId;
        this.groupName = groupName;
    }

    public ArtistGroup(){}

    @Id
    @Column(name = "artistid", updatable = false, nullable = false)
    @ApiModelProperty(hidden = true)
    private Long artistId;

    @Id
    @Column(name = "artistgroupid", nullable = false)
    private Long artistGroupId;

    @ApiModelProperty(hidden = true)
    @Transient
    private String groupName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artistgroupid", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    @ApiModelProperty(hidden = true)
    private Artist artist;

    public Long getArtistId() {
        return artistId;
    }

    public void setArtistId(Long artistId) {
        this.artistId = artistId;
    }

    public Long getArtistGroupId() {
        return artistGroupId;
    }

    public void setArtistGroupId(Long artistGroupId) {
        this.artistGroupId = artistGroupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }
}
