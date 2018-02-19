package kr.mubeat.cms.dto.es;

/**
 * Created by moonkyu.lee on 2017. 9. 14..
 */
public class ElasticSearchAggregationDTO {

    private String index;
    private String meta;
    private String payload;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
