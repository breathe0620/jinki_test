package kr.mubeat.cms.dto.meta.artist;

import com.querydsl.core.annotations.QueryProjection;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by moonkyu.lee on 2017. 8. 11..
 */
public class ArtistExcelDTO implements Serializable {

    private static final long serialVersionUID = 8795140554266596671L;

    private Long artistId;
    private Long originId;
    private String group;
    private String groupEn;
    private String artistName;
    private String artistNameEn;
    private Date createDt;
    private Date birthDt;
    private String deleteYn;

    @QueryProjection
    public ArtistExcelDTO(
            Long artistId,
            Long originId,
            String group,
            String groupEn,
            String artistName,
            String artistNameEn,
            Date createDt,
            Date birthDt,
            String deleteYn
    ) {
        this.artistId = artistId;
        this.originId = originId;
        this.group = group;
        this.groupEn = groupEn;
        this.artistName = artistName;
        this.artistNameEn = artistNameEn;
        this.createDt = createDt;
        this.birthDt = birthDt;
        this.deleteYn = deleteYn;
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

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getGroupEn() {
        return groupEn;
    }

    public void setGroupEn(String groupEn) {
        this.groupEn = groupEn;
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

    public Date getCreateDt() {
        return createDt;
    }

    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
    }

    public Date getBirthDt() {
        return birthDt;
    }

    public void setBirthDt(Date birthDt) {
        this.birthDt = birthDt;
    }

    public String getDeleteYn() {
        return deleteYn;
    }

    public void setDeleteYn(String deleteYn) {
        this.deleteYn = deleteYn;
    }
}
