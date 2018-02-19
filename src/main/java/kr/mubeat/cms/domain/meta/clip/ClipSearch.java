package kr.mubeat.cms.domain.meta.clip;

import java.util.Date;

/**
 * Created by moonkyu.lee on 2017. 6. 27..
 */
public class ClipSearch {
    /**
     * 페이지 관련
     */
    private int page;
    private int size = 15;

    private String searchText;
    /**
     * 1 : ID 로 검색
     * 2 : Text(String) (으)로 검색
     */
    private Integer type;
    private Date start;
    private Date end;

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
