package kr.mubeat.cms.dto.meta.song;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.ApiModelProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by doohwan.yoo on 2017. 5. 10..
 */
public class SongDTO implements Serializable {

    private static final long serialVersionUID = -4600199189626216253L;

    /**
     * List
     * @param songId
     * @param originId
     * @param songName
     * @param songNameEn
     * @param artistName
     * @param createDt
     */
    @QueryProjection
    public SongDTO(
            Long songId,
            Long originId,
            String songName,
            String songNameEn,
            String artistName,
            Date createDt
    ) {
        this.songId = songId;
        this.originId = originId;
        this.songName = songName;
        this.songNameEn = songNameEn;
        this.artistName = artistName;
        this.createDt = createDt;
    }

    /**
     * List & ElasticSearch
     * @param songId
     * @param originId
     * @param songName
     * @param artistName
     * @param createDt
     */
    @QueryProjection
    public SongDTO(
            Long songId,
            Long originId,
            String songName,
            String artistName,
            Date createDt
    ) {
        this.songId = songId;
        this.originId = originId;
        this.songName = songName;
        this.artistName = artistName;
        this.createDt = createDt;
    }

    /**
     *
     * @param songId
     * @param originId
     * @param songName
     * @param genreId
     * @param createDt
     * @param artistId
     * @param artistName
     * @param albumId
     * @param albumOriginId
     * @param albumName
     * @param publishDt
     * @param albumImgUrl
     * @param lyric
     */
    @QueryProjection
    public SongDTO(
            Long songId,
            Long originId,
            String songName,
            String songNameEn,
            Integer genreId,
            Date createDt,
            String artistId,
            String artistName,
            Long albumId,
            Long albumOriginId,
            String albumName,
            Date publishDt,
            String albumImgUrl,
            String lyric
    ) {
        this.songId = songId;
        this.originId = originId;
        this.songName = songName;
        this.songNameEn = songNameEn;
        this.genreId = genreId;
        this.createDt = createDt;
        this.artistId = artistId;
        this.artistName = artistName;
        this.albumId = albumId;
        this.albumOriginId = albumOriginId;
        this.albumName = albumName;
        this.publishDt = publishDt;
        this.albumImgUrl = albumImgUrl;
        this.lyric = lyric;
    }

    public SongDTO(){}

    @ApiModelProperty(hidden = true)
    private Long songId;

    private Long originId;

    @ApiModelProperty(required = true)
    private String songName;

    private String songNameEn;

    @ApiModelProperty(required = true)
    private Integer genreId;

    private String artistId;

    private Long albumId;

    private Long albumOriginId;

    private String albumName;

    private Date publishDt;

    private String lyric;

    @ApiModelProperty(hidden = true)
    private Date createDt;

    @ApiModelProperty(hidden = true)
    private String artistName;

    @ApiModelProperty(hidden = true)
    private Date modifyDt;

    private String albumImgUrl;

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

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSongNameEn() {
        return songNameEn;
    }

    public void setSongNameEn(String songNameEn) {
        this.songNameEn = songNameEn;
    }

    public Integer getGenreId() {
        return genreId;
    }

    public void setGenreId(Integer genreId) {
        this.genreId = genreId;
    }

    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public Long getAlbumOriginId() {
        return albumOriginId;
    }

    public void setAlbumOriginId(Long albumOriginId) {
        this.albumOriginId = albumOriginId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public Date getPublishDt() {
        return publishDt;
    }

    public void setPublishDt(Date publishDt) {
        this.publishDt = publishDt;
    }

    public String getLyric() {
//        if (lyric != null) {
//            this.lyric = lyric.replace("\"", "&#34");
//            this.lyric = lyric.replace("'", "&#39");
//            this.lyric = lyric.replace("-", "&#45");
//            this.lyric = lyric.replace("<", "&lt");
//            this.lyric = lyric.replace(">", "&gt");
//        }

        return lyric;
    }

    public void setLyric(String lyric) {
        this.lyric = lyric;
    }

    public Date getCreateDt() {
        return createDt;
    }

    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public Date getModifyDt() {
        return modifyDt;
    }

    public void setModifyDt(Date modifyDt) {
        this.modifyDt = modifyDt;
    }

    public String getAlbumImgUrl() {
        return albumImgUrl;
    }

    public void setAlbumImgUrl(String albumImgUrl) {
        this.albumImgUrl = albumImgUrl;
    }
}
