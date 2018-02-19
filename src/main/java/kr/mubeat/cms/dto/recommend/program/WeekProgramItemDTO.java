package kr.mubeat.cms.dto.recommend.program;

import java.io.Serializable;

/**
 * Created by moonkyu.lee on 2017. 11. 10..
 */
public class WeekProgramItemDTO implements Serializable {

    private static final long serialVersionUID = 8855971554166941369L;

    private Integer recommendProgramId;
    private Long clipMetaId;

    public WeekProgramItemDTO(
        Integer recommendProgramId,
        Long clipMetaId
    ) {
        this.recommendProgramId = recommendProgramId;
        this.clipMetaId = clipMetaId;
    }

    public WeekProgramItemDTO() {
    }

    public Integer getRecommendProgramId() {
        return recommendProgramId;
    }

    public void setRecommendProgramId(Integer recommendProgramId) {
        this.recommendProgramId = recommendProgramId;
    }

    public Long getClipMetaId() {
        return clipMetaId;
    }

    public void setClipMetaId(Long clipMetaId) {
        this.clipMetaId = clipMetaId;
    }
}
