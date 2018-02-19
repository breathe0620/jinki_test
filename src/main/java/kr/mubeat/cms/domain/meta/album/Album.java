package kr.mubeat.cms.domain.meta.album;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

/**
 * Created by minisv on 2017. 6. 8..
 */
@Entity
@Table(name = "tbl_album")
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "albumid", updatable = false, nullable = false)
    private Long albumId;

    @Column(name = "originid")
    private Long originId;

    @Column(name = "publishdt")
    private Date publishDt;

    @Column(name = "albumtypeid", nullable = false)
    private Integer albumTypeId;

    @Column(name = "albumimgurl")
    private String albumImgUrl;

    @Column(name = "deleteyn", nullable = false, insertable = false)
    private String deleteYn;

    @Column(name = "createdt", insertable = false, updatable = false)
    private Date createDt;

    @Column(name = "modifydt", insertable = false, updatable = false)
    private Date modifyDt;

    @OneToMany(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "albumid", updatable = false, insertable = false)
    private Collection<AlbumLang> albumLang;

    @OneToMany(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "albumid", updatable = false, insertable = false)
    private Collection<AlbumArtist> albumArtist;

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public Long getOriginId() {
        return originId;
    }

    public void setOriginId(Long originId) {
        this.originId = originId;
    }

    public Date getPublishDt() {
        return publishDt;
    }

    public void setPublishDt(Date publishDt) {
        this.publishDt = publishDt;
    }

    public Integer getAlbumTypeId() {
        return albumTypeId;
    }

    public void setAlbumTypeId(Integer albumTypeId) {
        this.albumTypeId = albumTypeId;
    }

    public String getAlbumImgUrl() {
        return albumImgUrl;
    }

    public void setAlbumImgUrl(String albumImgUrl) {
        this.albumImgUrl = albumImgUrl;
    }

    public String getDeleteYn() {
        return deleteYn;
    }

    public void setDeleteYn(String deleteYn) {
        this.deleteYn = deleteYn;
    }

    public Date getCreateDt() {
        return createDt;
    }

    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
    }

    public Date getModifyDt() {
        return modifyDt;
    }

    public void setModifyDt(Date modifyDt) {
        this.modifyDt = modifyDt;
    }

    public Collection<AlbumLang> getAlbumLang() {
        return albumLang;
    }

    public void setAlbumLang(Collection<AlbumLang> albumLang) {
        this.albumLang = albumLang;
    }

    public Collection<AlbumArtist> getAlbumArtist() {
        return albumArtist;
    }

    public void setAlbumArtist(Collection<AlbumArtist> albumArtist) {
        this.albumArtist = albumArtist;
    }
}
