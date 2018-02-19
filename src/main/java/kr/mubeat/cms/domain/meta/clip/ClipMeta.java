package kr.mubeat.cms.domain.meta.clip;

import kr.mubeat.cms.domain.meta.agency.Agency;
import kr.mubeat.cms.domain.meta.program.ProgramMeta;
import kr.mubeat.cms.domain.recommend.weekprogram.WeekProgramItem;
import kr.mubeat.cms.dto.meta.clip.ClipEsDTO;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 * Created by doohwan.yoo on 2017. 4. 26..
 * 클립 부가 정보
 */
@Entity
@SqlResultSetMapping(
    name = "ClipEsDTO",
    classes = {
        @ConstructorResult(
            targetClass = ClipEsDTO.class,
            columns = {
                @ColumnResult(name = "metaid", type = Long.class)
                ,@ColumnResult(name = "title", type = String.class)
                ,@ColumnResult(name = "titleen", type = String.class)
                ,@ColumnResult(name = "artists", type = String.class)
                ,@ColumnResult(name = "artistsen", type = String.class)
                ,@ColumnResult(name = "songs", type = String.class)
                ,@ColumnResult(name = "songsen", type = String.class)
            }
        )
    }
)
@Table(name = "tbl_clip_meta") // 트랜잭션 매니저를 붙여야 할 수 있음
public class ClipMeta implements Serializable {

    private static final long serialVersionUID = -2936963734491776611L;

    @Id
    @Column(name="clipmetaid", updatable = false, nullable = false)
    private Long clipMetaId;

    @Column(name="corporator", nullable = false)
    private String corporator;

    @Column(name="clipcategory", nullable = false)
    private String clipCategory;

    @Column(name="clipclassify")
    private Integer clipClassify;

    @Column(name = "programid")
    private String programId;

    @Column(name = "agencyid")
    private Integer agencyId;

    /**
     * 추가
     */
    @Column(name = "playtime")
    private Integer playTime;

    @Column(name="thumbimgurl")
    private String thumbImgUrl;

    @Column(name="broaddate")
    private String broadDate;

    @Column(name = "regdate", insertable = false, updatable = false)
    private String regDate;

    /**
     * 추가
     */
    @Column(name = "reservedate", insertable = false, updatable = false)
    private String reserveDate;

    @Column(name="enableyn", insertable = false, updatable = false)
    private String enableYn;

    @Column(name = "isoriginal", insertable = false, updatable = false)
    private String isoriginal;

    /**
     * 추가
     */
    @Column(name = "hit", insertable = false, updatable = false)
    private Integer hit;

    @Column(name="createdt", insertable = false, updatable = false)
    private Date creatDt;

    @Column(name="modifydt", insertable = false, updatable = false)
    private Date modifyDt;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "clipmetaid", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private Collection<ClipMetaLang> clipMetaLang;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clipmetaid", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private ClipAcquisition clipAcquisition;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "clipmetaid", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private Collection<ClipArtist> clipArtist;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "clipmetaid", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private Collection<ClipSong> clipSong;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "agencyid", insertable = false, updatable = false, referencedColumnName = "agencyid")
    @NotFound(action = NotFoundAction.IGNORE)
    private Collection<Agency> agency;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "programid", insertable = false, updatable = false, referencedColumnName = "programid")
    @NotFound(action = NotFoundAction.IGNORE)
    private Collection<ProgramMeta> programMeta;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "clipmetaid", insertable = false, updatable = false, referencedColumnName = "clipmetaid")
    @NotFound(action = NotFoundAction.IGNORE)
    private Collection<WeekProgramItem> weekProgramItem;

    public Long getClipMetaId() {
        return clipMetaId;
    }

    public void setClipMetaId(Long clipMetaId) {
        this.clipMetaId = clipMetaId;
    }

    public String getCorporator() {
        return corporator;
    }

    public void setCorporator(String corporator) {
        this.corporator = corporator;
    }

    public String getClipCategory() {
        return clipCategory;
    }

    public void setClipCategory(String clipCategory) {
        this.clipCategory = clipCategory;
    }

    public Integer getClipClassify() {
        return clipClassify;
    }

    public void setClipClassify(Integer clipClassify) {
        this.clipClassify = clipClassify;
    }

    public String getEnableYn() {
        return enableYn;
    }

    public void setEnableYn(String enableYn) {
        this.enableYn = enableYn;
    }

    public String getIsoriginal() {
        return isoriginal;
    }

    public void setIsoriginal(String isoriginal) {
        this.isoriginal = isoriginal;
    }

    public String getBroadDate() {
        return broadDate;
    }

    public void setBroadDate(String broadDate) {
        this.broadDate = broadDate;
    }

    public String getThumbImgUrl() {
        return thumbImgUrl;
    }

    public void setThumbImgUrl(String thumbImgUrl) {
        this.thumbImgUrl = thumbImgUrl;
    }

    public Date getCreatDt() {
        return creatDt;
    }

    public void setCreatDt(Date creatDt) {
        this.creatDt = creatDt;
    }

    public Date getModifyDt() {
        return modifyDt;
    }

    public void setModifyDt(Date modifyDt) {
        this.modifyDt = modifyDt;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public Collection<ClipMetaLang> getClipMetaLang() {
        return clipMetaLang;
    }

    public void setClipMetaLang(Collection<ClipMetaLang> clipMetaLang) {
        this.clipMetaLang = clipMetaLang;
    }

    public Collection<ClipArtist> getClipArtist() {
        return clipArtist;
    }

    public void setClipArtist(Collection<ClipArtist> clipArtist) {
        this.clipArtist = clipArtist;
    }

    public Collection<ClipSong> getClipSong() {
        return clipSong;
    }

    public void setClipSong(Collection<ClipSong> clipSong) {
        this.clipSong = clipSong;
    }

    public Integer getPlayTime() {
        return playTime;
    }

    public void setPlayTime(Integer playTime) {
        this.playTime = playTime;
    }

    public String getReserveDate() {
        return reserveDate;
    }

    public void setReserveDate(String reserveDate) {
        this.reserveDate = reserveDate;
    }

    public Integer getHit() {
        return hit;
    }

    public void setHit(Integer hit) {
        this.hit = hit;
    }

    public ClipAcquisition getClipAcquisition() {
        return clipAcquisition;
    }

    public void setClipAcquisition(ClipAcquisition clipAcquisition) {
        this.clipAcquisition = clipAcquisition;
    }

    public String getProgramId() {
        return programId;
    }

    public void setProgramId(String programId) {
        this.programId = programId;
    }

    public Integer getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Integer agencyId) {
        this.agencyId = agencyId;
    }

    public Collection<Agency> getAgency() {
        return agency;
    }

    public void setAgency(Collection<Agency> agency) {
        this.agency = agency;
    }

    public Collection<ProgramMeta> getProgramMeta() {
        return programMeta;
    }

    public void setProgramMeta(Collection<ProgramMeta> programMeta) {
        this.programMeta = programMeta;
    }

    public Collection<WeekProgramItem> getWeekProgramItem() {
        return weekProgramItem;
    }

    public void setWeekProgramItem(Collection<WeekProgramItem> weekProgramItem) {
        this.weekProgramItem = weekProgramItem;
    }
}
