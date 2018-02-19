package kr.mubeat.cms.enumerations;

/**
 * Created by moonkyu.lee on 2017. 8. 29..
 */
public enum RedisStoreNames {

    SPARK_REQUEST("emr:spark:request"),
    SPARK_RESULT("emr:spark:result");

    private String storeType;

    RedisStoreNames(String storeType) {
        this.storeType = storeType;
    }

    public String getStoreType() {
        return storeType;
    }
}
