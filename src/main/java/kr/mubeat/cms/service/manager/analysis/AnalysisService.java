package kr.mubeat.cms.service.manager.analysis;

import kr.mubeat.cms.dto.manager.analysis.SparkRequestParam;

/**
 * Created by moonkyu.lee on 2017. 8. 25..
 */
public interface AnalysisService {

    void requestAnalysisRetentionCohortChartBySpark(SparkRequestParam params);

    String getResult(SparkRequestParam param);

}
