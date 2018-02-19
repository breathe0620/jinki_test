package kr.mubeat.cms.controller.manager;

import kr.mubeat.cms.annotation.admin.SuperAdmin;
import kr.mubeat.cms.dto.Result;
import kr.mubeat.cms.dto.es.ElasticSearchAggregationDTO;
import kr.mubeat.cms.dto.manager.analysis.SparkRequestParam;
import kr.mubeat.cms.enumerations.EMRSparkRequestType;
import kr.mubeat.cms.enumerations.EnumResCode;
import kr.mubeat.cms.service.manager.analysis.AnalysisService;
import kr.mubeat.cms.util.ElasticsearchUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

/**
 * Created by moonkyu.lee on 2017. 8. 25..
 */
@Controller
@RequestMapping(value = "/analysis")
@RolesAllowed("MENU_MEMBER")
@SuperAdmin
public class AnalysisController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private AnalysisService analysisService;
    private ElasticsearchUtil elasticsearchUtil;

    @Autowired
    public AnalysisController(
            AnalysisService analysisService,
            ElasticsearchUtil elasticsearchUtil
    ) {
        this.analysisService = analysisService;
        this.elasticsearchUtil = elasticsearchUtil;
    }

    /**
     * 스파크 분석 페이지 요청
     * 개발 방식 변경으로 중단 하였으나, 차후 다른 용도로 사용 할 수 있어서 남겨둠
     * @return
     */
    @SuperAdmin
    @RequestMapping(value = "/spark", method = RequestMethod.GET)
    public String spark() {
        return "/manager/analysis";
    }

    /**
     * 스파크 분석 요청
     * @param param
     * @return
     */
    @SuperAdmin
    @RequestMapping(value = "/jobRequest", method = RequestMethod.GET)
    public @ResponseBody
    Result jobRequest(
            @ModelAttribute SparkRequestParam param
    ) {
        String data = analysisService.getResult(param);
        if (data == null || data == "") {
            analysisService.requestAnalysisRetentionCohortChartBySpark(
                    param
            );
            return new Result(EnumResCode.OK);
        } else {
            Result result = new Result(EnumResCode.OK);
            result.setData(data);
            return result;
        }
    }

    /**
     * ElasticSearch 에 직접 Query 실행 시키기 페이지 호출
     * @return
     */
    @RequestMapping(value = "/customQuery", method = RequestMethod.GET)
    public String customQuery() {
        return "analysis/custom_query";
    }

    /**
     * ElasticSearch 에 직접 Query 실행 시키기
     * @param param
     * @return
     */
    @RequestMapping(value = "/customQuery", method = RequestMethod.POST)
    public @ResponseBody String customQueryResult(
        @RequestBody ElasticSearchAggregationDTO param
        ) {
        return elasticsearchUtil.aggregation(param);
    }

}
