package kr.mubeat.cms.dto;

import org.springframework.data.domain.Page;

import java.util.Date;

/**
 * Created by doohwan.yoo on 2017. 6. 27..
 */
public class CustomPageResult<T> {
    private Page<T> pageResult;
    private String searchText;
    /**
     * 1 : ID 로 검색
     * 2 : Text(String) (으)로 검색
     * 3 : 날짜 범위로만 검색
     */
    private Integer type;
    private Date start;
    private Date end;

    /**
     * 클립 복합 검색 관련 데이터
     */
    private String corporator;
    private String program;
    private String clipType;
    private String uploadState;
    private String transcodeState;
    private Integer clipClassify;
    private String enableState;

    public CustomPageResult() {
    }

    public CustomPageResult(CustomSearchParam searchParam) {
        this.searchText = searchParam.getSearchText();
        this.type = searchParam.getType();
        this.start = searchParam.getStart();
        this.end = searchParam.getEnd();
        this.corporator = searchParam.getCorporator();
        this.program = searchParam.getProgram();
        this.clipType = searchParam.getClipType();
        this.uploadState = searchParam.getUploadState();
        this.transcodeState = searchParam.getTranscodeState();
        this.clipClassify = searchParam.getClipClassify();
        this.enableState = searchParam.getEnableState();
    }

    public Page<T> getPageResult() {
        return pageResult;
    }

    public void setPageResult(Page<T> pageResult) {
        this.pageResult = pageResult;
    }

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
