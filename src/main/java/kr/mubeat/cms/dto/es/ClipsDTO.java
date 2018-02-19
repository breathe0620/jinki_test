package kr.mubeat.cms.dto.es;

import java.io.Serializable;

/**
 *
 * Created by moonkyu.lee on 2017. 7. 27..
 */
public class ClipsDTO implements Serializable {

    private static final long serialVersionUID = -2405866190641507278L;

    private Long clipMetaId;
    private String clipTitle;
    private String keyword;

    public ClipsDTO(
            Long clipMetaId,
            String clipTitle
    ) {
        this.clipMetaId = clipMetaId;
        this.clipTitle = clipTitle;
        setKeyword(getClipTitle());
    }

    public Long getClipMetaId() {
        return clipMetaId;
    }

    public void setClipMetaId(Long clipMetaId) {
        this.clipMetaId = clipMetaId;
    }

    public String getClipTitle() {
        return clipTitle;
    }

    public void setClipTitle(String clipTitle) {
        this.clipTitle = clipTitle;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword.replaceAll(" ", "");
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + clipMetaId +
                ", \"title\":\"" + clipTitle + "\"" +
                ", \"keyword\":\"" + keyword + "\"" +
                '}';
    }
}
