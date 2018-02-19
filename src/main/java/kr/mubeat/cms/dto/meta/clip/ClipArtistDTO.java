package kr.mubeat.cms.dto.meta.clip;

import com.querydsl.core.annotations.QueryProjection;

import java.io.Serializable;

/**
 * Created by moonkyu.lee on 2017. 6. 26..
 */
public class ClipArtistDTO implements Serializable {

    private static final long serialVersionUID = -623149078555176016L;

    @QueryProjection
    public ClipArtistDTO(Long artistId, String artistName) {
        this.artistId = artistId;
        this.artistName = artistName;
    }

    public ClipArtistDTO(){}

    private Long artistId;
    private String artistName;

    public Long getArtistId() {
        return artistId;
    }

    public void setArtistId(Long artistId) {
        this.artistId = artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }
}
