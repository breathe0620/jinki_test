package kr.mubeat.cms.service.manager.analysis.impl;

import com.amazonaws.services.elasticmapreduce.AmazonElasticMapReduce;
import com.amazonaws.services.elasticmapreduce.model.*;
import kr.mubeat.cms.dto.manager.analysis.SparkRequestParam;
import kr.mubeat.cms.enumerations.RedisStoreNames;
import kr.mubeat.cms.service.manager.analysis.AnalysisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

/**
 * Created by moonkyu.lee on 2017. 8. 25..
 */
@Service
public class AnalysisServiceImpl implements AnalysisService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private AmazonElasticMapReduce amazonElasticMapReduce;

    private RedisTemplate redisTemplate;

    @Value("${spring.aws.spark-job-flow-id}")
    private String jobId;

    @Autowired
    public AnalysisServiceImpl(
            AmazonElasticMapReduce amazonElasticMapReduce,
            RedisTemplate redisTemplate
    ) {
        this.amazonElasticMapReduce = amazonElasticMapReduce;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void requestAnalysisRetentionCohortChartBySpark(
            SparkRequestParam param
    ) {
        /**
         * TODO
         * 1. Service 로 Spark Add Job
         * 2. redis 에 STATUS Add Job 상태로 변경
         * 3. sparkStatus 에 값 전달
         */

        HadoopJarStepConfig sparkStepConf = new HadoopJarStepConfig()
                .withJar("command-runner.jar")
                .withArgs("spark-submit",
                        "--deploy-mode","client",
                        "--verbose",
                        "--master","yarn",
                        "--class","com.mubeat.RetentionCohort",
                        "s3://mubeat-logs/jar/RetentionCohort-1.0-SNAPSHOT.jar",
                        "yarn", param.getDate(), param.getEnvironment(), String.valueOf(param.isLog()), param.getStartDate());

        StepConfig sparkStep = new StepConfig()
                .withName("Retention Cohort")
                .withActionOnFailure(ActionOnFailure.CONTINUE)
                .withHadoopJarStep(sparkStepConf);

        AddJobFlowStepsRequest req = new AddJobFlowStepsRequest();
        req.withJobFlowId(jobId);
        req.withSteps(sparkStep);
        AddJobFlowStepsResult result = amazonElasticMapReduce.addJobFlowSteps(req);

    }

    @Override
    public String getResult(SparkRequestParam param) {
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        return String.valueOf(
                redisTemplate.opsForHash().get(
                        RedisStoreNames.SPARK_RESULT.getStoreType()+":"+param.getType().name(),
                        param.getStartDate()
                )
        );
    }

    private String getEMRJobStatus(String stepId) {
        DescribeStepRequest describeStepRequest = new DescribeStepRequest().withStepId(stepId);
        describeStepRequest.setClusterId(jobId);
        return amazonElasticMapReduce.describeStep(
                describeStepRequest
        ).getStep().getStatus().getState();
    }
}
