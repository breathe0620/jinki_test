package kr.mubeat.cms.util;

import kr.mubeat.cms.dto.es.ElasticSearchAggregationDTO;
import kr.mubeat.cms.enumerations.AWSServices;
import kr.mubeat.cms.enumerations.es.EsOrder;
import kr.mubeat.cms.enumerations.es.EsSearchType;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by moonkyu.lee on 2017. 6. 29..
 */
@Service
public class ElasticsearchUtil {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${spring.elasticsearch.host}")
    private String host;

    private AWSV4RequestUtil awsv4RequestUtil;

    @Autowired
    public ElasticsearchUtil(AWSV4RequestUtil awsv4RequestUtil) {
        this.awsv4RequestUtil = awsv4RequestUtil;
    }

    public String aggregation(ElasticSearchAggregationDTO param) {
        StringBuilder queryString = new StringBuilder();
        if (param.getIndex() != null && !param.getIndex().equalsIgnoreCase("null") && param.getIndex().length() > 0) {
            queryString.append("/");
            queryString.append(param.getIndex());
        }
        if (param.getMeta() != null && !param.getMeta().equalsIgnoreCase("null") && param.getMeta().length() > 0) {
            queryString.append("/");
            queryString.append(param.getMeta());
        }
        queryString.append("/_search");

        return awsv4RequestUtil.request(
            RequestMethod.POST,
            AWSServices.es,
            host,
            queryString.toString(),
            param.getPayload()
        );
    }

    public JSONObject getById(String index, String id) {
        String result = awsv4RequestUtil.request(
                RequestMethod.GET,
                AWSServices.es,
                host,
//                "/meta/"+ type + "/" + id,
                "/" + index + "/doc/" + id,
                null
        );

        if (result != null && result != "null") {
            JSONObject jsonObject = new JSONObject(String.valueOf(result));
            return (JSONObject)jsonObject.get("_source");
        } else {
            return null;
        }
    }

    /**
     * Elasticsearch 심플 검색
     * @param index      artist, album, song ...
     * @param search    빅뱅 ...
     * @return
     */
    public JSONObject searchItem(int from, String index, String search) {
        return this.searchItem(from, index, EsSearchType.MATCH_PHRASE_PREFIX, search, null, null);
    }

    /**
     * Elasticsearch 정렬 검색
     * @param index   artist, album, song ...
     * @param search 빅뱅 ...
     * @param sort   name ...
     * @param order
     * @return
     */
    public JSONObject searchItem(int from, String index, EsSearchType searchType, String search, String sort, EsOrder order) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"query\":{\"");
        sb.append(searchType.getSearchType());
        sb.append("\":{\"name\":\"");
        sb.append(search);
        sb.append("\"}}");
        // es text filed 를 fielddate = true를 하지 않았기에 사용하지 않음
//        if (sort != null && order != null) {
//            sb.append(",\"sort\":[{\""+sort+"\":{\"order\":\""
//                    + order.getOrder() + "\"}}]");
//        }
        sb.append(",\"from\":");
        sb.append(from);
        sb.append(",\"size\":20}");

        String result = awsv4RequestUtil.request(
                RequestMethod.POST,
                AWSServices.es,
                host,
//  es index 구조 변경
//                "/meta/"+index+"/_search",
                "/" + index + "/doc/_search",
                sb.toString()
        );

        JSONObject jsonObject = new JSONObject(String.valueOf(result));
        return (JSONObject)jsonObject.get("hits");
    }

    /**
     * ElasticSearch 아이템 등록/수정
     * @param metaInfo /meta/artist ...
     * @param id        id ...
     * @param payload   {"originid":0,"name":"빅뱅"}
     * @return
     */
    public boolean putItem(String metaInfo, Long id, String payload) {
        String result = awsv4RequestUtil.request(
                RequestMethod.PUT,
                AWSServices.es,
                host,
                "/" + metaInfo + "/" + id,
                payload
        );

        return checkResult(result);
    }

    /**
     * ElasticSearch 아이템 업데이트
     * @param metaInfo
     * @param id
     * @param payload
     * @return
     */
    public boolean updateItem(String metaInfo, Long id, String payload) {
        String result = awsv4RequestUtil.request(
                RequestMethod.POST,
                AWSServices.es,
                host,
                metaInfo+"/"+id+"/_update",
                payload
        );

        return checkResult(result);
    }

    /**
     * ElasticSearch 아이템 삭제
     * @param metaInfo  /meta/artist ...
     * @param id        id ...
     * @return
     */
    public boolean deleteItem(String metaInfo, Long id) {
        /**
         * {
         "found": true,
         "_index": "meta",
         "_type": "clip",
         "_id": "47673",
         "_version": 3,
         "result": "deleted",
         "_shards": {
         "total": 2,
         "successful": 1,
         "failed": 0
         }
         }
         */
        String result = awsv4RequestUtil.request(
                RequestMethod.DELETE,
                AWSServices.es,
                host,
                metaInfo+"/"+id,
                null
        );

        return checkResult(result);
    }

    private boolean checkResult(String result) {
        try {
            if (result.equalsIgnoreCase("null")) {
                return false;
            }

            JSONObject jsonObject = new JSONObject(result);
            JSONObject shards = (JSONObject)jsonObject.get("_shards");
            Integer successful = Integer.parseInt(String.valueOf(shards.get("successful")));

            return successful > 0;
        } catch (Exception e) {}
        return false;
    }

}
