package kr.mubeat.cms.dto.manager.analysis;

import kr.mubeat.cms.enumerations.EMRSparkRequestType;

import java.io.Serializable;

/**
 * Created by moonkyu.lee on 2017. 8. 28..
 */
public class SparkRequestParam implements Serializable {

    private static final long serialVersionUID = -308917168665514102L;

    /**
     * date         yyyy/MM/dd 포맷으로 입력
     * environment  live or dev
     * log          로그 남길지 여부
     */
    private EMRSparkRequestType type;
    private String startDate;
    private String date;
    private String environment;
    private boolean log;

    public EMRSparkRequestType getType() {
        return type;
    }

    public void setType(EMRSparkRequestType type) {
        this.type = type;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public boolean isLog() {
        return log;
    }

    public void setLog(boolean log) {
        this.log = log;
    }
}
