package kr.mubeat.cms.dto.meta.clip;

import com.querydsl.core.annotations.QueryProjection;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by moonkyu.lee on 2017. 10. 23..
 */
public class ClipExcelDTO implements Serializable {

    private static final long serialVersionUID = 3360420666041664726L;

    private Long clipMetaId;
    private String corporator;
    private String programTitle;
    private String programTitleEn;
    private String clipTitle;
    private String clipTitleEn;
    private String clipCategory;
    private Integer clipClassify;
    private Date modifyDt;
    private String enableYn;
    private String clipTitleEnGuide;

    public ClipExcelDTO() {
    }

    @QueryProjection
    public ClipExcelDTO(
            Long clipMetaId,
            String corporator,
            String programTitle,
            String programTitleEn,
            String clipTitle,
            String clipTitleEn,
            String clipCategory,
            Integer clipClassify,
            Date modifyDt,
            String enableYn,
            String artistName,
            String songName
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
        /**
         * 가볍게 가이드를 풀어준 경우
         */
//        this.clipTitleEnGuide = "["+programTitleEn+"] " + artistName + " - " + songName;
        /**
         * 조건을 강화 한 경우
         */
        if (
//            (programTitleEn != null && !programTitleEn.equalsIgnoreCase("null") && programTitleEn.trim().length() > 0)
//                &&
            (artistName != null && !artistName.equalsIgnoreCase("null") && artistName.trim().length() > 0)
                &&
            (songName != null && !songName.equalsIgnoreCase("null") && songName.trim().length() > 0)
                &&
            clipCategory.equalsIgnoreCase("03")
        ) {
//            this.clipTitleEnGuide = "["+programTitleEn+"] " + artistName + " - " + songName;
            this.clipTitleEnGuide = artistName + " - " + songName;
        } else {
            this.clipTitleEnGuide = "";
        }
    }

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

    public Integer getClipClassify() {
        return clipClassify;
    }

    public void setClipClassify(Integer clipClassify) {
        this.clipClassify = clipClassify;
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

    public String getClipTitleEnGuide() {
        return clipTitleEnGuide;
    }

    public void setClipTitleEnGuide(String clipTitleEnGuide) {
        this.clipTitleEnGuide = clipTitleEnGuide;
    }
}
