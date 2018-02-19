package kr.mubeat.cms.enumerations.es;

/**
 * Created by moonkyu.lee on 2017. 6. 29..
 */
public enum EsOrder {

    ASC("asc"),
    DSC("dsc");

    private String order;
    EsOrder(String order) { this.order = order; }

    public String getOrder() { return this.order; }

}
