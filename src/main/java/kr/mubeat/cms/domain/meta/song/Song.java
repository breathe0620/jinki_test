package kr.mubeat.cms.domain.meta.song;

import kr.mubeat.cms.domain.meta.album.Album;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

/**
 * Created by doohwan.yoo on 2017. 5. 10..
 */
@Entity
@Table(name = "tbl_song")
public class Song {

    /** ToDo
     * 추후 클러스터나 샤딩이 들어가면 별도 ID 생성 제네레이터가 들어가야 한다
     @GeneratedValue(strategy=GenerationType.TABLE , generator="employee_generator")
     @TableGenerator(name="employee_generator",
     table="pk_table",
     pkColumnName="name",
     valueColumnName="value",
     allocationSize=100)
     */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="songid", updatable = false, nullable = false)
    private Long songId;

    @Column(name="originid")
    private Long originId;

    @Column(name="genreid", nullable = false)
    private Integer genreId;

    @Column(name = "albumid")
    private Long albumId;

    @Column(name = "lyric")
    private String lyric;

    @Column(name="deleteyn", nullable = false, insertable = false)
    private String deleteYn;

    @Column(name="createdt", insertable = false, updatable = false)
    private Date createDt;

    @Column(name="modifyDt", insertable = false, updatable = false)
    private Date modifyDt;

    @OneToMany(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "songid", updatable = false, insertable = false)
    private Collection<SongLang> songLang;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "albumid", updatable = false, insertable = false)
    private Album album;

    @OneToMany(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "songid", updatable = false, insertable = false)
    private Collection<SongArtist> songArtist;

    public Long getSongId() {
        return songId;
    }

    public void setSongId(Long songId) {
        this.songId = songId;
    }

    public Long getOriginId() {
        return originId;
    }

    public void setOriginId(Long originId) {
        this.originId = originId;
    }

    public Integer getGenreId() {
        return genreId;
    }

    public void setGenreId(Integer genreId) {
        this.genreId = genreId;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public String getLyric() {
        return lyric;
    }

    public void setLyric(String lyric) {
        this.lyric = lyric;
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

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Collection<SongLang> getSongLang() {
        return songLang;
    }

    public void setSongLang(Collection<SongLang> songLang) {
        this.songLang = songLang;
    }

    public Collection<SongArtist> getSongArtist() {
        return songArtist;
    }

    public void setSongArtist(Collection<SongArtist> songArtist) {
        this.songArtist = songArtist;
    }
}
