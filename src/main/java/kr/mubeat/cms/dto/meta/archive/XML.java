package kr.mubeat.cms.dto.meta.archive;

import java.io.Serializable;

/**
 * Created by moonkyu.lee on 2017. 9. 20..
 */
public class XML implements Serializable {

    private static final long serialVersionUID = 6696487082374919833L;

    private String corporator;
    private String date;
    private String code;
    private String name;
    private String fullName;

    public XML(String data) {
        this.fullName = data;

        this.corporator = data.substring(0, data.indexOf("/"));
        String reduce = data.substring(data.indexOf("/") + 1).replace(".xml", "");
        this.date = reduce.substring(0, reduce.indexOf("_"));
        this.code = reduce.substring(reduce.lastIndexOf("_") + 1, reduce.length());
        String name = reduce.replace(this.date, "").replace(this.code, "");
        name = name.substring(1, name.length());
        this.name = name.substring(0, name.indexOf("_"));
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

    public String getFullName() {
        return fullName;
    }
}
