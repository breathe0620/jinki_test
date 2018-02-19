package kr.mubeat.cms.domain.es;

/**
 * Created by doohwan.yoo on 2017. 5. 22..
 */
public class EsResult {
    private Long total;
    private Object data;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
