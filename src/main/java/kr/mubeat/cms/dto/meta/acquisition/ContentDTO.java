package kr.mubeat.cms.dto.meta.acquisition;

import com.querydsl.core.annotations.QueryProjection;

import java.io.Serializable;

/**
 * Created by moonkyu.lee on 2017. 9. 5..
 */
public class ContentDTO implements Serializable {

    private static final long serialVersionUID = -4034257032273443848L;

    private String number;
    private String broadDate;

    @QueryProjection
    public ContentDTO(
        String number,
        String broadDate
    ) {
        this.number = number;
        this.broadDate = broadDate;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBroadDate() {
        return broadDate;
    }

    public void setBroadDate(String broadDate) {
        this.broadDate = broadDate;
    }
}
