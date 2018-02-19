package kr.mubeat.cms.dto.meta.clip;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.querydsl.core.annotations.QueryProjection;
import kr.mubeat.cms.domain.meta.clip.ClipArtist;
import kr.mubeat.cms.domain.meta.clip.ClipMedia;
import kr.mubeat.cms.domain.meta.clip.ClipSong;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by moonkyu.lee on 2017. 6. 20..
 */
public class ClipDTO implements Serializable {

    private static final long serialVersionUID = 9221559415280692197L;

    /**
     * Excel
     * @param clipMetaId
     * @param corporator
     * @param programTitle
     * @param clipTitle
     * @param clipCategory
     * @param clipClassify
     * @param modifyDt
     * @param enableYn
     */
    @QueryProjection
    public ClipDTO(
            Long clipMetaId,
            String corporator,
            String programTitle,
            String programTitleEn,
            String clipTitle,
            String clipTitleEn,
            String clipCategory,
            Integer clipClassify,
            Date modifyDt,
            String enableYn
    ) {
        this.clipMetaId = clipMetaId;
        this.corporator = corporator;
        this.programTitle = programTitle;
        this.programTitleEn = programTitleEn;
        this.clipTitle = clipTitle;
        this.clipTitleEn = clipTitleEn;
        this.clipCategory = clipCategory;
        this.clipClassify = clipClassify;
        this.modifyDt = modifyDt;
        this.enableYn = enableYn;
    }

    /**
     * List
     * @param clipMetaId
     * @param corporator
     * @param programTitle
     * @param clipTitle
     * @param clipCategory
     * @param modifyDt
     * @param enableYn
     * @param clipClassify
     * @param artistRegist
     * @param songRegist
     */
    @QueryProjection
    public ClipDTO(
            Long clipMetaId,      // 클립 메타 아이디
            String corporator,      // 방송사
            String programTitle,    // 프로그램명
            String clipTitle,       // 클립 이름
            String clipCategory,    // 클립 카테고리
            Date modifyDt,          // 수정일
            String enableYn,         // 노출 여부
            Integer clipClassify,   // 상세 카테고리
            Long artistRegist,       // 아티스트 값 등록
            Long songRegist          // 송 값 등록
    ) {
        this.clipMetaId = clipMetaId;
        this.corporator = corporator;
        this.programTitle = programTitle;
        this.clipTitle = clipTitle;
        this.clipCategory = clipCategory;
        this.modifyDt = modifyDt;
        this.enableYn = enableYn;
        this.clipClassify = clipClassify;
        this.artistRegist = artistRegist;
        this.songRegist = songRegist;
    }

    /**
     * 클립 상세
     * @param clipMetaId
     * @param corporator
     * @param clipId
     * @param programTitle
     * @param clipTitle
     * @param clipCategory
     * @param contentNumber
     * @param modifyDt
     * @param enableYn
     * @param broadDt
     * @param isoriginal
     * @param playTime
     * @param clipClassify
     * @param clipUrl
     */
    @QueryProjection
    public ClipDTO(
            Long clipMetaId,            // 클립 메타 아이디
            String corporator,          // 방송사
            String clipId,              // 클립 아이디
            String programId,
            String programTitle,        // 프로그램명
            String programTitleEn,        // 프로그램명 영문
            String clipTitle,           // 클립 이름
            String clipTitleEn,           // 클립 이름 영문
            String clipCategory,        // 클립 카테고리
            String contentNumber,       // 콘텐츠 번호
            Date modifyDt,              // 수정일
            String enableYn,              // 노출 여부
            String broadDt,               // 방송일              program // content
            String isoriginal,          // 독점 여부
            Integer playTime,           // 플레이 시간
            Integer clipClassify,  // 상세 카테고리
            String clipMainImgUrl,      // 메인 이미지 url
            String clipUrl              // 원본 영상
    ) {
        this.clipMetaId = clipMetaId;
        this.corporator = corporator;
        this.clipId = clipId;
        this.programId = programId;
        this.programTitle = programTitle;
        this.programTitleEn = programTitleEn;
        this.clipTitle = clipTitle;
        this.clipTitleEn = clipTitleEn;
        this.clipCategory = clipCategory;
        this.contentNumber = contentNumber;
        this.modifyDt = modifyDt;
        this.enableYn = enableYn;
        this.broadDt = broadDt;
        this.isoriginal = isoriginal;
        this.playTime = playTime;
        this.clipClassify = clipClassify;
        this.clipMainImgUrl = clipMainImgUrl;
        this.clipUrl = clipUrl;
    }

    public ClipDTO(){}

    @ApiModelProperty(hidden = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Long clipMetaId;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String corporator;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String clipId;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String programId;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String programTitle;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String programTitleEn;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String clipTitle;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String clipTitleEn;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String clipCategory;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String contentNumber;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Date createDt;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Date modifyDt;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String enableYn;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Long artistRegist;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Long songRegist;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String broadDt;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String isoriginal;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer playTime;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer clipClassify;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String clipMainImgUrl;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String clipUrl;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ClipArtist> artistList;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ClipSong> songList;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ClipMedia> clipMediaList;

    public Long getClipMetaId() {
        return clipMetaId;
    }

    public void setClipMetaId(Long clipMetaId) {
        this.clipMetaId = clipMetaId;
    }

    public String getClipId() {
        return clipId;
    }

    public void setClipId(String clipId) {
        this.clipId = clipId;
    }

    public String getCorporator() {
        return corporator;
    }

    public void setCorporator(String corporator) {
        this.corporator = corporator;
    }

    public String getProgramId() {
        return programId;
    }

    public void setProgramId(String programId) {
        this.programId = programId;
    }

    public String getProgramTitle() {
        return programTitle;
    }

    public void setProgramTitle(String programTitle) {
        this.programTitle = programTitle;
    }

    public String getProgramTitleEn() {
        return programTitleEn;
    }

    public void setProgramTitleEn(String programTitleEn) {
        this.programTitleEn = programTitleEn;
    }

    public String getClipTitle() {
        return clipTitle;
    }

    public void setClipTitle(String clipTitle) {
        this.clipTitle = clipTitle;
    }

    public String getClipTitleEn() {
        return clipTitleEn;
    }

    public void setClipTitleEn(String clipTitleEn) {
        this.clipTitleEn = clipTitleEn;
    }

    public String getClipCategory() {
        return clipCategory;
    }

    public void setClipCategory(String clipCategory) {
        this.clipCategory = clipCategory;
    }

    public String getContentNumber() {
        return contentNumber;
    }

    public void setContentNumber(String contentNumber) {
        this.contentNumber = contentNumber;
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

    public String getEnableYn() {
        return enableYn;
    }

    public void setEnableYn(String enableYn) {
        this.enableYn = enableYn;
    }

    public Long getArtistRegist() {
        return artistRegist;
    }

    public void setArtistRegist(Long artistRegist) {
        this.artistRegist = artistRegist;
    }

    public Long getSongRegist() {
        return songRegist;
    }

    public void setSongRegist(Long songRegist) {
        this.songRegist = songRegist;
    }

    public String getBroadDt() {
        return broadDt;
    }

    public void setBroadDt(String broadDt) {
        this.broadDt = broadDt;
    }

    public String getIsoriginal() {
        return isoriginal;
    }

    public void setIsoriginal(String isoriginal) {
        this.isoriginal = isoriginal;
    }

    public Integer getPlayTime() {
        return playTime;
    }

    public void setPlayTime(Integer playTime) {
        this.playTime = playTime;
    }

    public Integer getClipClassify() {
        return clipClassify;
    }

    public void setClipClassify(Integer clipClassify) {
        this.clipClassify = clipClassify;
    }

    public String getClipMainImgUrl() {
        return clipMainImgUrl;
    }

    public void setClipMainImgUrl(String clipMainImgUrl) {
        this.clipMainImgUrl = clipMainImgUrl;
    }

    public String getClipUrl() {
        return clipUrl;
    }

    public void setClipUrl(String clipUrl) {
        this.clipUrl = clipUrl;
    }

    public List<ClipArtist> getArtistList() {
        return artistList;
    }

    public void setArtistList(List<ClipArtist> artistList) {
        this.artistList = artistList;
    }

    public List<ClipSong> getSongList() {
        return songList;
    }

    public void setSongList(List<ClipSong> songList) {
        this.songList = songList;
    }

    public List<ClipMedia> getClipMediaList() {
        return clipMediaList;
    }

    public void setClipMediaList(List<ClipMedia> clipMediaList) {
        this.clipMediaList = clipMediaList;
    }

}
