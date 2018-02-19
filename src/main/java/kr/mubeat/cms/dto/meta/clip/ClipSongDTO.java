package kr.mubeat.cms.dto.meta.clip;

import com.querydsl.core.annotations.QueryProjection;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by moonkyu.lee on 2017. 6. 26..
 */
public class ClipSongDTO implements Serializable {

    private static final long serialVersionUID = 4493826260539842477L;

    @QueryProjection
    public ClipSongDTO(Long songId, String songTitle, Date songPublishDt, String songGenre) {
        this.songId = songId;
        this.songTitle = songTitle;
        this.songPublishDt = songPublishDt;
        this.songGenre = songGenre;
    }

    public ClipSongDTO(){}

    private Long songId;
    private String songTitle;
    private Date songPublishDt;
    private String songGenre;

    public Long getSongId() {
        return songId;
    }

    public void setSongId(Long songId) {
        this.songId = songId;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public Date getSongPublishDt() {
        return songPublishDt;
    }

    public void setSongPublishDt(Date songPublishDt) {
        this.songPublishDt = songPublishDt;
    }

    public String getSongGenre() {
        return songGenre;
    }

    public void setSongGenre(String songGenre) {
        this.songGenre = songGenre;
    }
}
