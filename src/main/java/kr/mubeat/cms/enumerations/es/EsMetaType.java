package kr.mubeat.cms.enumerations.es;

/**
 * Created by moonkyu.lee on 2017. 6. 29..
 */
public enum EsMetaType {

    ARTIST("artist"),
    ALBUM("album"),
    SONG("song"),
    CLIP("clip");

    private String metaType;
    EsMetaType(String metaType) { this.metaType = metaType; }

    public String getMetaType() { return this.metaType; }

}
