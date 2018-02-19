package kr.mubeat.cms.domain.meta.clip;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by doohwan.yoo on 2017. 4. 27..
 */
@Entity
@Table(name = "tbl_content")
public class Content extends BaseInfo implements Serializable {

    private static final long serialVersionUID = -3050640708142618766L;

    @Id
    @Column(name = "contentid", updatable = false, nullable = false)
    private String contentId;

    @Column(name = "cornerid", nullable = false)
    private String cornerId;

    @Column(name = "contentnumber", nullable = false)
    private String contentNumber;

    @Column(name = "cornernumber", nullable = false)
    private String cornerNumber;

    @Column(name = "preview")
    private String preview;

    @Column(name = "broaddate")
    private String broadDate;

    @Column(name = "contentimg")
    private String contentImg;

    @Column(name = "actor")
    private String actor;

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getCornerId() {
        return cornerId;
    }

    public void setCornerId(String cornerId) {
        this.cornerId = cornerId;
    }

    public String getContentNumber() {
        return contentNumber;
    }

    public void setContentNumber(String contentNumber) {
        this.contentNumber = contentNumber;
    }

    public String getCornerNumber() {
        return cornerNumber;
    }

    public void setCornerNumber(String cornerNumber) {
        this.cornerNumber = cornerNumber;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public String getBroadDate() {
        return broadDate;
    }

    public void setBroadDate(String broadDate) {
        this.broadDate = broadDate;
    }

    public String getContentImg() {
        return contentImg;
    }

    public void setContentImg(String contentImg) {
        this.contentImg = contentImg;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }
}
