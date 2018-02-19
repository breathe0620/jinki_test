package kr.mubeat.cms.dto.recommend.theme;

import java.io.Serializable;

/**
 * Created by moonkyu.lee on 2017. 11. 7..
 */
public class ThemePosition implements Serializable {

    private static final long serialVersionUID = 4428144321675838933L;

    private Integer recommendThemeId;
    private Integer position;

    public ThemePosition(
        Integer recommendThemeId,
        Integer position
    ) {
        this.recommendThemeId = recommendThemeId;
        this.position = position;
    }

    public ThemePosition() {
    }

    public Integer getRecommendThemeId() {
        return recommendThemeId;
    }

    public void setRecommendThemeId(Integer recommendThemeId) {
        this.recommendThemeId = recommendThemeId;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}
