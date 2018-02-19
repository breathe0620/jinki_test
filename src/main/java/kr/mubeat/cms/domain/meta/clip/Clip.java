package kr.mubeat.cms.domain.meta.clip;

/*
 * Created by doohwan.yoo on 2017. 4. 26..
 * 클립 입수 정보
 */

import kr.mubeat.cms.dto.meta.clip.ClipDTO;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@SqlResultSetMapping(
    name = "ClipDTO",
    classes = {
        @ConstructorResult(
            targetClass = ClipDTO.class,
            columns = {
                @ColumnResult(name = "clipmetaid", type = Long.class)
                ,@ColumnResult(name = "corporator", type = String.class)
                ,@ColumnResult(name = "programtitle", type = String.class)
                ,@ColumnResult(name = "cliptitle", type = String.class)
                ,@ColumnResult(name = "clipcategory", type = String.class)
                ,@ColumnResult(name = "modifydt", type = Date.class)
                ,@ColumnResult(name = "enableyn", type = String.class)
                ,@ColumnResult(name = "clipclassify", type = Integer.class)
                ,@ColumnResult(name = "artistregist", type = Long.class)
                ,@ColumnResult(name = "songregist", type = Long.class)
            }
        )
    }
)
@Table(name = "tbl_clip")
public class Clip extends BaseInfo implements Serializable {

    private static final long serialVersionUID = 3877977188386254683L;

    @Id
    @Column(name = "clipid", updatable = false, nullable = false)
    private String clipId;

    @Column(name = "programid", nullable = false)
    private String programId;

    @Column(name = "cliporder")
    private int clipOrder;

    @Column(name = "channelid")
    private String channelId;

    @Column(name = "contentid")
    private String contentId;

    @Column(name = "cornerid")
    private int cornerId;

    @Column(name = "synopsis")
    private String synopsis;

    @Column(name = "mediadomain", nullable = false)
    private String mediaDomain;

    @Column(name = "filepath", nullable = false)
    private String filePath;

    @Column(name = "itemtypeid", nullable = false)
    private int itemTypeId;

    @Column(name = "clipcategory", nullable = false)
    private String clipCategory;

    @Column(name = "subcategory")
    private String subCategory;

    @Column(name = "filemodifydate", nullable = false)
    private String fileModifyDate;

    @Column(name = "reservedate")
    private String reserveDate;

    @Column(name = "contentimg")
    private String contentImg;

    @Column(name = "playtime", nullable = false)
    private Integer playTime;

    @Column(name = "starttime")
    private Integer startTime;

    @Column(name = "endtime")
    private Integer endTime;

    @Column(name = "platformisuse")
    private String platformIsUse;

    @Column(name = "returnlink")
    private String returnLink;

    @Column(name = "linktitle1")
    private String linkTitle1;

    @Column(name = "linkurl1")
    private String linkurl1;

    @Column(name = "linktitle2")
    private String linkTitle2;

    @Column(name = "linkurl2")
    private String linkurl2;

    @Column(name = "linktitle3")
    private String linkTitle3;

    @Column(name = "linkurl3")
    private String linkurl3;

    @Column(name = "linktitle4")
    private String linkTitle4;

    @Column(name = "linkurl4")
    private String linkurl4;

    @Column(name = "linktitle5")
    private String linkTitle5;

    @Column(name = "linkurl5")
    private String linkurl5;

    @Column(name = "masterclip")
    private String masterClip;

    @Column(name = "broaddate")
    private String broadDate;

    @Column(name = "isfullvod")
    private String isFullVod;

    @Column(name = "sportscomment")
    private String sportsComment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="programid", updatable = false, insertable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private Program program;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name="contentid", updatable = false, insertable = false)
    private Content content;

    public String getClipId() {
        return clipId;
    }

    public void setClipId(String clipId) {
        this.clipId = clipId;
    }

    public String getProgramId() {
        return programId;
    }

    public void setProgramId(String programId) {
        this.programId = programId;
    }

    public int getClipOrder() {
        return clipOrder;
    }

    public void setClipOrder(int clipOrder) {
        this.clipOrder = clipOrder;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public int getCornerId() {
        return cornerId;
    }

    public void setCornerId(int cornerId) {
        this.cornerId = cornerId;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getMediaDomain() {
        return mediaDomain;
    }

    public void setMediaDomain(String mediaDomain) {
        this.mediaDomain = mediaDomain;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getItemTypeId() {
        return itemTypeId;
    }

    public void setItemTypeId(int itemTypeId) {
        this.itemTypeId = itemTypeId;
    }

    public String getClipCategory() {
        return clipCategory;
    }

    public void setClipCategory(String clipCategory) {
        this.clipCategory = clipCategory;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getFileModifyDate() {
        return fileModifyDate;
    }

    public void setFileModifyDate(String fileModifyDate) {
        this.fileModifyDate = fileModifyDate;
    }

    public String getReserveDate() {
        return reserveDate;
    }

    public void setReserveDate(String reserveDate) {
        this.reserveDate = reserveDate;
    }

    public String getContentImg() {
        return contentImg;
    }

    public void setContentImg(String contentImg) {
        this.contentImg = contentImg;
    }

    public Integer getPlayTime() {
        return playTime;
    }

    public void setPlayTime(Integer playTime) {
        this.playTime = playTime;
    }

    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

    public String getPlatformIsUse() {
        return platformIsUse;
    }

    public void setPlatformIsUse(String platformIsUse) {
        this.platformIsUse = platformIsUse;
    }

    public String getReturnLink() {
        return returnLink;
    }

    public void setReturnLink(String returnLink) {
        this.returnLink = returnLink;
    }

    public String getLinkTitle1() {
        return linkTitle1;
    }

    public void setLinkTitle1(String linkTitle1) {
        this.linkTitle1 = linkTitle1;
    }

    public String getLinkurl1() {
        return linkurl1;
    }

    public void setLinkurl1(String linkurl1) {
        this.linkurl1 = linkurl1;
    }

    public String getLinkTitle2() {
        return linkTitle2;
    }

    public void setLinkTitle2(String linkTitle2) {
        this.linkTitle2 = linkTitle2;
    }

    public String getLinkurl2() {
        return linkurl2;
    }

    public void setLinkurl2(String linkurl2) {
        this.linkurl2 = linkurl2;
    }

    public String getLinkTitle3() {
        return linkTitle3;
    }

    public void setLinkTitle3(String linkTitle3) {
        this.linkTitle3 = linkTitle3;
    }

    public String getLinkurl3() {
        return linkurl3;
    }

    public void setLinkurl3(String linkurl3) {
        this.linkurl3 = linkurl3;
    }

    public String getLinkTitle4() {
        return linkTitle4;
    }

    public void setLinkTitle4(String linkTitle4) {
        this.linkTitle4 = linkTitle4;
    }

    public String getLinkurl4() {
        return linkurl4;
    }

    public void setLinkurl4(String linkurl4) {
        this.linkurl4 = linkurl4;
    }

    public String getLinkTitle5() {
        return linkTitle5;
    }

    public void setLinkTitle5(String linkTitle5) {
        this.linkTitle5 = linkTitle5;
    }

    public String getLinkurl5() {
        return linkurl5;
    }

    public void setLinkurl5(String linkurl5) {
        this.linkurl5 = linkurl5;
    }

    public String getMasterClip() {
        return masterClip;
    }

    public void setMasterClip(String masterClip) {
        this.masterClip = masterClip;
    }

    public String getBroadDate() {
        return broadDate;
    }

    public void setBroadDate(String broadDate) {
        this.broadDate = broadDate;
    }

    public String getIsFullVod() {
        return isFullVod;
    }

    public void setIsFullVod(String isFullVod) {
        this.isFullVod = isFullVod;
    }

    public String getSportsComment() {
        return sportsComment;
    }

    public void setSportsComment(String sportsComment) {
        this.sportsComment = sportsComment;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }
}
