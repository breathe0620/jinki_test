package kr.mubeat.cms.domain.meta.clip;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by moonkyu.lee on 2017. 6. 26..
 */
@Entity
@Table(name = "tbl_clip_classify")
public class Classify {

    @Id
    @Column(name = "clipclassify")
    private Integer clipClassify;

    @Column(name = "name")
    private String name;

    public Integer getClipClassify() {
        return clipClassify;
    }

    public void setClipClassify(Integer clipClassify) {
        this.clipClassify = clipClassify;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
