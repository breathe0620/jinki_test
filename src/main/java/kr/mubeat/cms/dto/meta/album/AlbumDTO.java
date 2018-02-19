package kr.mubeat.cms.dto.meta.album;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by moonkyu.lee on 2017. 6. 8..
 */
public class AlbumDTO implements Serializable {

    private static final long serialVersionUID = 8538142800901691694L;

    /**
     * List
     * @param albumId
     * @param originId
     * @param albumTitle
     * @param albumTitleEn
     * @param artistName
     * @param publishDt
     */
    @QueryProjection
    public AlbumDTO(
            Long albumId,
            Long originId,
            String albumTitle,
            String albumTitleEN,
            String artistName,
            Date publishDt
    ) {
        this.albumId = albumId;
        this.originId = originId;
        this.albumTitle = albumTitle;
        this.albumTitleEN = albumTitleEN;
        this.artistName = artistName;
        this.publishDt = publishDt;
    }

    /**
     * List & ElasticSearch
     * @param albumId
     * @param originId
     * @param albumTitle
     * @param artistName
     * @param publishDt
     */
    @QueryProjection
    public AlbumDTO(
            Long albumId,
            Long originId,
            String albumTitle,
            String artistName,
            Date publishDt
    ) {
        this.albumId = albumId;
        this.originId = originId;
        this.albumTitle = albumTitle;
        this.artistName = artistName;
        this.publishDt = publishDt;
    }

    /**
     * Detail
     * @param albumId
     * @param originId
     * @param artistId
     * @param artistOriginId
     * @param albumTitle
     * @param albumTypeId
     * @param albumImgUrl
     * @param createDt
     * @param publishDt
     * @param artistName
     */
    @QueryProjection
    public AlbumDTO(
            Long albumId,
            Long originId,
            String artistId,
            Long artistOriginId,
            String albumTitle,
            String albumTitleEn,
            Integer albumTypeId,
            String albumImgUrl,
            Date createDt,
            Date publishDt,
            String artistName
    ) {
        this.albumId = albumId;
        this.originId = originId;
        this.artistId = artistId;
        this.artistOriginId = artistOriginId;
        this.albumTitle = albumTitle;
        this.albumTitleEN = albumTitleEn;
        this.albumTypeId = albumTypeId;
        this.albumImgUrl = albumImgUrl;
        this.createDt = createDt;
        this.publishDt = publishDt;
        this.artistName = artistName;
    }

    public AlbumDTO(){}

    @ApiModelProperty(hidden = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Long albumId;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Long originId;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String artistId;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Long artistOriginId;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String albumTitle;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String albumTitleEN;

    @ApiModelProperty(required = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer albumTypeId;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String albumImgUrl;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Date createDt;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Date publishDt;

    @ApiModelProperty(hidden = true)
    private String artistName;

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

    public String getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    public String getAlbumTitleEN() {
        return albumTitleEN;
    }

    public void setAlbumTitleEN(String albumTitleEN) {
        this.albumTitleEN = albumTitleEN;
    }

    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId.trim();
    }

    public Long getArtistOriginId() {
        return artistOriginId;
    }

    public void setArtistOriginId(Long artistOriginId) {
        this.artistOriginId = artistOriginId;
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

    public Date getCreateDt() {
        return createDt;
    }

    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
    }

    public Date getPublishDt() {
        return publishDt;
    }

    public void setPublishDt(Date publishDt) {
        this.publishDt = publishDt;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }
}
