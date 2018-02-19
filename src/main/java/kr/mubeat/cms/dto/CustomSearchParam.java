package kr.mubeat.cms.dto;

import kr.mubeat.cms.config.ClipState;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by doohwan.yoo on 2017. 6. 27..
 */
public class CustomSearchParam {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private int page = 0;
    private int size = 15;

    private String searchText;
    private Integer type;
    private Date start;
    private Date end;
    /**
     * 클립 복합 검색
     */
    private String corporator;
    private String program;
    private String clipType;
    private String uploadState = ClipState.FAIL;
    private String transcodeState = ClipState.FAIL;
    private Integer clipClassify;
    private String enableState;

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

    public String getSearchText() {
        if (this.searchText != null) {
            this.searchText = searchText.trim();
        }
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = StringEscapeUtils.unescapeHtml4(searchText.trim());
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
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(end);
        calendar.add(Calendar.HOUR, 23);
        calendar.add(Calendar.MINUTE, 59);
        calendar.add(Calendar.SECOND, 59);
        this.end = calendar.getTime();
    }

    public String getCorporator() {
        return corporator;
    }

    public void setCorporator(String corporator) {
        this.corporator = corporator;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getClipType() {
        return clipType;
    }

    public void setClipType(String clipType) {
        this.clipType = clipType;
    }

    public String getUploadState() {
        return uploadState;
    }

    public void setUploadState(String uploadState) {
        this.uploadState = uploadState;
    }

    public String getTranscodeState() {
        return transcodeState;
    }

    public void setTranscodeState(String transcodeState) {
        this.transcodeState = transcodeState;
    }

    public Integer getClipClassify() {
        return clipClassify;
    }

    public void setClipClassify(Integer clipClassify) {
        this.clipClassify = clipClassify;
    }

    public String getEnableState() {
        return enableState;
    }

    public void setEnableState(String enableState) {
        this.enableState = enableState;
    }
}
