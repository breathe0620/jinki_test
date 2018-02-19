package kr.mubeat.cms.domain.meta.clip;

import kr.mubeat.cms.domain.complexkey.ClipMetaLangKey;
import kr.mubeat.cms.dto.meta.clip.ClipExcelDTO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by moonkyu.lee on 2017. 9. 6..
 */
@Entity
@SqlResultSetMapping(
    name = "ClipExcelDTO",
    classes = {
        @ConstructorResult(
            targetClass = ClipExcelDTO.class,
            columns = {
                @ColumnResult(name = "clipmetaid", type = Long.class)
                ,@ColumnResult(name = "corporator", type = String.class)
                ,@ColumnResult(name = "programtitle", type = String.class)
                ,@ColumnResult(name = "programtitleen", type = String.class)
                ,@ColumnResult(name = "clipTitle", type = String.class)
                ,@ColumnResult(name = "clipTitleEn", type = String.class)
                ,@ColumnResult(name = "clipCategory", type = String.class)
                ,@ColumnResult(name = "clipClassify", type = Integer.class)
                ,@ColumnResult(name = "modifyDt", type = Date.class)
                ,@ColumnResult(name = "enableYn", type = String.class)
                ,@ColumnResult(name = "artistName", type = String.class)
                ,@ColumnResult(name = "songName", type = String.class)
            }
        )
    }
)
@IdClass(ClipMetaLangKey.class)
@Table(name = "tbl_clip_meta_lang")
public class ClipMetaLang implements Serializable {

    private static final long serialVersionUID = 8608914229186968695L;

    @Id
    @Column(name = "clipmetaid", updatable = false, nullable = false)
    private Long clipMetaId;

    @Id
    @Column(name = "langtype", nullable = false)
    private String langType;

    @Column(name = "cliptitle", nullable = false)
    private String clipTitle;

    public ClipMetaLang() {
    }

    public ClipMetaLang(
            Long clipMetaId,
            String langType,
            String clipTitle
    ) {
        this.clipMetaId = clipMetaId;
        this.langType = langType;
        this.clipTitle = clipTitle;
    }

    public Long getClipMetaId() {
        return clipMetaId;
    }

    public void setClipMetaId(Long clipMetaId) {
        this.clipMetaId = clipMetaId;
    }

    public String getLangType() {
        return langType;
    }

    public void setLangType(String langType) {
        this.langType = langType;
    }

    public String getClipTitle() {
        return clipTitle;
    }

    public void setClipTitle(String clipTitle) {
        this.clipTitle = clipTitle;
    }
}
