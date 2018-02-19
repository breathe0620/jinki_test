package kr.mubeat.cms.enumerations.es;

/**
 * Created by moonkyu.lee on 2017. 6. 29..
 */
public enum EsSearchType {

    MATCH("match"),
    MATCH_PHRASE_PREFIX("match_phrase_prefix");

    private String searchType;
    EsSearchType(String searchType) { this.searchType = searchType; }

    public String getSearchType() { return this.searchType; }

}
