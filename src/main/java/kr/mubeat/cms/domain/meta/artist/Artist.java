package kr.mubeat.cms.domain.meta.artist;

import kr.mubeat.cms.domain.meta.agency.Agency;
import kr.mubeat.cms.domain.meta.song.Song;
import kr.mubeat.cms.dto.meta.artist.ArtistExcelDTO;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

/**
 * Created by doohwan.yoo on 2017. 5. 10..
 */
@Entity
@SqlResultSetMapping(
        name = "ArtistExcelDTO",
        classes = {
                @ConstructorResult(
                        targetClass = ArtistExcelDTO.class,
                        columns = {
                                @ColumnResult(name = "artistid", type = Long.class),
                                @ColumnResult(name = "originid", type = Long.class),
                                @ColumnResult(name = "group", type = String.class),
                                @ColumnResult(name = "groupen", type = String.class),
                                @ColumnResult(name = "artistname", type = String.class),
                                @ColumnResult(name = "artistnameen", type = String.class),
                                @ColumnResult(name = "createdt", type = Date.class),
                                @ColumnResult(name = "birthdt", type = Date.class),
                                @ColumnResult(name = "deleteyn", type = String.class)
                        }
                )
        }
)
@Table(name = "tbl_artist")
public class Artist {

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
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="artistid", updatable = false, nullable = false)
    private Long artistId;

    @Column(name="originid")
    private Long originId;

    @Column(name="artisttypeid", nullable = false)
    private Integer artistTypeId;

    @Column(name="debutdt")
    private Date debutDt;

    @Column(name="debutsongid")
    private Long debutSongId;

    @Column(name="organizedt")
    private Date organizeDt;

    @Column(name="agencyid")
    private Integer agencyId;

    @Column(name="artistmainimgurl")
    private String artistMainImgUrl;

    @Column(name="artistprofileimgurl")
    private String artistProfileImgUrl;

    @Column(name="deleteyn", nullable = false, insertable = false)
    private String deleteYn;

    @Column(name="createdt", insertable = false, updatable = false)
    private Date createDt;

    @Column(name="modifydt", insertable = false, updatable = false)
    private Date modifyDt;

    @Column(name = "birthdt")
    private Date birthDt;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "artistid", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private Collection<ArtistLang> artistLang;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artisttypeid", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private ArtistType artistType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agencyid", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private Agency agency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "debutsongid", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private Song song;

    public Long getArtistId() {
        return artistId;
    }

    public void setArtistId(Long artistId) {
        this.artistId = artistId;
    }

    public Long getOriginId() {
        return originId;
    }

    public void setOriginId(Long originId) {
        this.originId = originId;
    }

    public Integer getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Integer agencyId) {
        this.agencyId = agencyId;
    }

    public Integer getArtistTypeId() {
        return artistTypeId;
    }

    public void setArtistTypeId(Integer artistTypeId) {
        this.artistTypeId = artistTypeId;
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

    public Date getDebutDt() {
        return debutDt;
    }

    public void setDebutDt(Date debutDt) {
        this.debutDt = debutDt;
    }

    public Long getDebutSongId() {
        return debutSongId;
    }

    public void setDebutSongId(Long debutSongId) {
        this.debutSongId = debutSongId;
    }

    public Date getOrganizeDt() {
        return organizeDt;
    }

    public void setOrganizeDt(Date organizeDt) {
        this.organizeDt = organizeDt;
    }

    public String getArtistMainImgUrl() {
        return artistMainImgUrl;
    }

    public void setArtistMainImgUrl(String artistMainImgUrl) {
        this.artistMainImgUrl = artistMainImgUrl;
    }

    public String getArtistProfileImgUrl() {
        return artistProfileImgUrl;
    }

    public void setArtistProfileImgUrl(String artistProfileImgUrl) {
        this.artistProfileImgUrl = artistProfileImgUrl;
    }

    public ArtistType getArtistType() {
        return artistType;
    }

    public void setArtistType(ArtistType artistType) {
        this.artistType = artistType;
    }

    public Agency getAgency() {
        return agency;
    }

    public void setAgency(Agency agency) {
        this.agency = agency;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public Date getBirthDt() {
        return birthDt;
    }

    public void setBirthDt(Date birthDt) {
        this.birthDt = birthDt;
    }

    public Collection<ArtistLang> getArtistLang() {
        return artistLang;
    }

    public void setArtistLang(Collection<ArtistLang> artistLang) {
        this.artistLang = artistLang;
    }
}
