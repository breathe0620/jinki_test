package kr.mubeat.cms.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import kr.mubeat.cms.enumerations.EnumResCode;

import java.io.Serializable;

/**
 * Created by doohwan.yoo on 2017. 4. 18..
 */
@JsonSerialize
public class Result implements Serializable {

    private static final long serialVersionUID = -456561072358591575L;

    private String code;
    private String msg;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Object data;

    public Result(EnumResCode resCode) {
        code = resCode.getCode();
        msg = resCode.getMessage();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
