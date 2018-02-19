package kr.mubeat.cms.domain.meta.artist;

import kr.mubeat.cms.domain.complexkey.ActiveAgesKey;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by doohwan.yoo on 2017. 6. 8..
 */
@Entity
@IdClass(ActiveAgesKey.class)
@Table(name="tbl_artist_activeages")
public class ArtistActiveages implements Serializable{

    private static final long serialVersionUID = 7544607241168533061L;

    @Id
    @Column(name="artistid", nullable = false, insertable = false, updatable = false)
    @ApiModelProperty(hidden = true)
    private Long artistId;

    @Id
    @Column(name="activeages", nullable = false)
    private String activeAges;

    public Long getArtistId() {
        return artistId;
    }

    public void setArtistId(Long artistId) {
        this.artistId = artistId;
    }

    public String getActiveAges() {
        return activeAges;
    }

    public void setActiveAges(String activeAges) {
        this.activeAges = activeAges;
    }
}
