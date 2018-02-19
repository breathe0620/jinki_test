package kr.mubeat.cms.dto.meta.artist;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.ApiModelProperty;
import kr.mubeat.cms.domain.meta.artist.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by doohwan.yoo on 2017. 5. 15..
 */
public class ArtistDTO implements Serializable {

    private static final long serialVersionUID = -8142183122993420737L;

    /**
     * List
     * @param artistId
     * @param originId
     * @param artistName
     * @param artistNameEn
     * @param createDt
     */
    @QueryProjection
    public ArtistDTO(
            Long artistId,
            Long originId,
            String artistName,
            String artistNameEn,
            Date createDt
    ) {
        this.artistId = artistId;
        this.originId = originId;
        this.artistName = artistName;
        this.artistNameEn = artistNameEn;
        this.createDt = createDt;
    }

    /**
     * Same Agency Artist
     * @param artistId
     * @param originId
     * @param artistName
     */
    @QueryProjection
    public ArtistDTO(
            Long artistId,
            Long originId,
            String artistName
    ) {
        this.artistId = artistId;
        this.originId = originId;
        this.artistName = artistName;
    }

    /**
     * List, ElasticSearch
     * @param artistId
     * @param originId
     * @param artistName
     * @param createDt
     */
    @QueryProjection
    public ArtistDTO(
            Long artistId,
            Long originId,
            String artistName,
            Date createDt
    ) {
        this.artistId = artistId;
        this.originId = originId;
        this.artistName = artistName;
        this.createDt = createDt;
    }

    /**
     * Detail
     * @param artistId
     * @param originId
     * @param artistName
     * @param artistTypeId
     * @param artistTypeName
     * @param debutDt
     * @param debutSongId
     * @param debutSongName
     * @param organizeDt
     * @param agencyId
     * @param agencyName
     * @param mainImgUrl
     * @param profileImgUrl
     * @param createDt
     * @param modifyDt
     * @param birthDt
     */
    @QueryProjection
    public ArtistDTO(
            Long artistId,
            Long originId,
            String artistName,
            String artistNameEn,
            Integer artistTypeId,
            String artistTypeName,
            Date debutDt,
            Long debutSongId,
            String debutSongName,
            Date organizeDt,
            Integer agencyId,
            String agencyName,
            String mainImgUrl,
            String profileImgUrl,
            Date createDt,
            Date modifyDt,
            Date birthDt
    ) {
        this.artistId = artistId;
        this.originId = originId;
        this.artistName = artistName;
        this.artistNameEn = artistNameEn;
        this.artistTypeId = artistTypeId;
        this.artistTypeName = artistTypeName;
        this.debutDt = debutDt;
        this.debutSongId = debutSongId;
        this.debutSongName = debutSongName;
        this.organizeDt = organizeDt;
        this.agencyId = agencyId;
        this.agencyName = agencyName;
        this.mainImgUrl = mainImgUrl;
        this.profileImgUrl = profileImgUrl;
        this.createDt = createDt;
        this.modifyDt = modifyDt;
        this.birthDt = birthDt;
    }

    public ArtistDTO(){}

    @ApiModelProperty(hidden = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Long artistId;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Long originId;

    @ApiModelProperty(required = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String artistName;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String artistNameEn;

    @ApiModelProperty(required = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer artistTypeId;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String artistTypeName;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ArtistActiveages> activeAges;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Date debutDt;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Long debutSongId;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String debutSongName;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Date organizeDt;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer agencyId;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String agencyName;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String mainImgUrl;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String profileImgUrl;

    @ApiModelProperty(hidden = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Date createDt;

    @ApiModelProperty(hidden = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Date modifyDt;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Date birthDt;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ArtistGroup> artistGroup;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<MemberArtist> memberArtist;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<RelativeArtist> relativeArtist;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<SimilarArtist> similarArtist;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ArtistDTO> sameAgencyArtist;

    public List<ArtistGroup> getArtistGroup() {
        return artistGroup;
    }

    public void setArtistGroup(List<ArtistGroup> artistGroup) {
        this.artistGroup = artistGroup;
    }

    public List<MemberArtist> getMemberArtist() {
        return memberArtist;
    }

    public void setMemberArtist(List<MemberArtist> memberArtist) {
        this.memberArtist = memberArtist;
    }

    public List<RelativeArtist> getRelativeArtist() {
        return relativeArtist;
    }

    public void setRelativeArtist(List<RelativeArtist> relativeArtist) {
        this.relativeArtist = relativeArtist;
    }

    public List<SimilarArtist> getSimilarArtist() {
        return similarArtist;
    }

    public void setSimilarArtist(List<SimilarArtist> similarArtist) {
        this.similarArtist = similarArtist;
    }


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

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getArtistNameEn() {
        return artistNameEn;
    }

    public void setArtistNameEn(String artistNameEn) {
        this.artistNameEn = artistNameEn;
    }

    public Integer getArtistTypeId() {
        return artistTypeId;
    }

    public void setArtistTypeId(Integer artistTypeId) {
        this.artistTypeId = artistTypeId;
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

    public List<ArtistActiveages> getActiveAges() {
        return activeAges;
    }

    public void setActiveAges(List<ArtistActiveages> activeAges) {
        this.activeAges = activeAges;
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

    public Integer getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Integer agencyId) {
        this.agencyId = agencyId;
    }

    public String getDebutSongName() {
        return debutSongName;
    }

    public void setDebutSongName(String debutSongName) {
        this.debutSongName = debutSongName;
    }

    public String getArtistTypeName() {
        return artistTypeName;
    }

    public void setArtistTypeName(String artistTypenName) {
        this.artistTypeName = artistTypenName;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getMainImgUrl() {
        return mainImgUrl;
    }

    public void setMainImgUrl(String mainImgUrl) {
        this.mainImgUrl = mainImgUrl;
    }

    public String getProfileImgUrl() {
        return profileImgUrl;
    }

    public void setProfileImgUrl(String profileImgUrl) {
        this.profileImgUrl = profileImgUrl;
    }

    public List<ArtistDTO> getSameAgencyArtist() {
        return sameAgencyArtist;
    }

    public void setSameAgencyArtist(List<ArtistDTO> sameAgencyArtist) {
        this.sameAgencyArtist = sameAgencyArtist;
    }

    public Date getBirthDt() {
        return birthDt;
    }

    public void setBirthDt(Date birthDt) {
        this.birthDt = birthDt;
    }
}
