package kr.mubeat.cms.dto.meta.archive;

import java.io.Serializable;

/**
 * Created by moonkyu.lee on 2017. 9. 20..
 */
public class ArchiveMBC implements Serializable {

    private static final long serialVersionUID = -4962456275133704486L;

    private String originXML;
    private String originVideo;

    private String xml;
    private String title;
    private Integer playtime;
    private String broadDate;

    public ArchiveMBC(
        String xml,
        String title,
        Integer playtime,
        String broadDate
    ) {
        this.xml = xml;
        this.title = title;
        this.playtime = playtime;
        this.broadDate = broadDate;
    }

    public String getOriginXML() {
        return originXML;
    }

    public void setOriginXML(String originXML) {
        this.originXML = originXML;
    }

    public String getOriginVideo() {
        return originVideo;
    }

    public void setOriginVideo(String originVideo) {
        this.originVideo = originVideo;
    }

    public String getXml() {
        return xml;
    }

    public String getTitle() {
        return title;
    }

    public Integer getPlaytime() {
        return playtime;
    }

    public String getBroadDate() {
        return broadDate;
    }
}
