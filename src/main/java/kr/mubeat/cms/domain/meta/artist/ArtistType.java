package kr.mubeat.cms.domain.meta.artist;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by doohwan.yoo on 2017. 5. 10..
 */
@Entity
@Table(name = "tbl_artist_type")
public class ArtistType implements Serializable {

    private static final long serialVersionUID = 1075032372016772238L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="artisttypeid", nullable = false, insertable = false, updatable = false)
    private Integer artistTypeId;

    // TODO @Id 필요 정리
    @Column(name="typename", nullable = false)
    private String typeName;

    public Integer getArtistTypeId() {
        return artistTypeId;
    }

    public void setArtistTypeId(Integer artistTypeId) {
        this.artistTypeId = artistTypeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
