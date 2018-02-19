package kr.mubeat.cms.dto.meta.clip;

import com.querydsl.core.annotations.QueryProjection;

import java.io.Serializable;

/**
 * Created by moonkyu.lee on 2017. 6. 27..
 */
public class ClassifyDTO implements Serializable {

    private static final long serialVersionUID = 1051103212048863033L;

    @QueryProjection
    public ClassifyDTO(Integer clipClassify, String name) {
        this.clipClassify = clipClassify;
        this.name = name;
    }

    private Integer clipClassify;
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
