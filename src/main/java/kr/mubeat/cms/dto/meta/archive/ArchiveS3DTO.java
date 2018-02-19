package kr.mubeat.cms.dto.meta.archive;

import java.io.Serializable;

/**
 * Created by moonkyu.lee on 2017. 9. 20..
 */
public class ArchiveS3DTO implements Serializable {

    private static final long serialVersionUID = 7979648063487706260L;

    private String corporator;
    private String date;
    private String code;
    private String name;
    private String xml;
    private String video;

    public ArchiveS3DTO(
        String corporator,
        String date,
        String code,
        String name,
        String xml,
        String video
    ) {
        this.corporator = corporator;
        this.date = date;
        this.code = code;
        this.name = name;
        this.xml = xml;
        this.video = video;
    }

    public String getCorporator() {
        return corporator;
    }

    public String getDate() {
        return date;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getXml() {
        return xml;
    }

    public String getVideo() {
        return video;
    }
}
