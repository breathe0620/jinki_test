package kr.mubeat.cms.domain.meta.album;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by minisv on 2017. 6. 8..
 */
@Entity
@Table(name = "tbl_album_type")
public class AlbumType implements Serializable {

    private static final long serialVersionUID = 1059718543648875106L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "albumtypeid", nullable = false, insertable = false, updatable = false)
    private Integer albumTypeId;

    @Column(name = "albumtypename", nullable = false)
    private String albumTypeName;

    public Integer getAlbumTypeId() {
        return albumTypeId;
    }

    public void setAlbumTypeId(Integer albumTypeId) {
        this.albumTypeId = albumTypeId;
    }

    public String getAlbumTypeName() {
        return albumTypeName;
    }

    public void setAlbumTypeName(String albumTypeName) {
        this.albumTypeName = albumTypeName;
    }
}
