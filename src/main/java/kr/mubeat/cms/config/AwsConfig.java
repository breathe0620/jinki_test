package kr.mubeat.cms.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cloudfront.AmazonCloudFrontClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.elasticmapreduce.AmazonElasticMapReduce;
import com.amazonaws.services.elasticmapreduce.AmazonElasticMapReduceClientBuilder;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by doohwan.yoo on 2017. 6. 8..
 */
@Configuration
public class AwsConfig {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${spring.aws.access-key}")
    private String accessKey;

    @Value("${spring.aws.secret-key}")
    private String secretkey;

    @Value("${spring.aws.region}")
    private String region;

    @Value("${spring.aws.dynamodb-local-endpoint}")
    private String dynamodbLocalEndpoint;

    @Value("${spring.aws.dynamodb-local-signing-region}")
    private String dynamodbLocalSigningRegion;

    @Bean
    public BasicAWSCredentials basicAWSCredentials() {
        return new BasicAWSCredentials(accessKey, secretkey);
    }

    @Bean
    public TransferManager transferManager() {
        TransferManager transferManager = new TransferManager(basicAWSCredentials());
        return transferManager;
    }

    @Bean
    public AmazonS3Client amazonS3Client(AWSCredentials awsCredentials) {
        AmazonS3Client amazonS3Client = new AmazonS3Client(awsCredentials);
        amazonS3Client.setRegion(Region.getRegion(Regions.fromName(region)));

        return amazonS3Client;
    }

    @Bean
    public AmazonCloudFrontClient amazonCloudFrontClient(AWSCredentials awsCredentials) {
        AmazonCloudFrontClient amazonCloudFrontClient = new AmazonCloudFrontClient(awsCredentials);
        amazonCloudFrontClient.setRegion(Region.getRegion(Regions.fromName(region)));

        return amazonCloudFrontClient;
    }

    @Bean
    public AmazonElasticMapReduce amazonElasticMapReduce(AWSCredentials awsCredentials) {
        return AmazonElasticMapReduceClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(region)
                .build();
    }

    @Bean
    public AmazonSimpleEmailServiceClient amazonSimpleEmailServiceClient(AWSCredentials awsCredentials) {
        return new AmazonSimpleEmailServiceClient(awsCredentials);
    }

    @Bean
    public AmazonDynamoDB amazonDynamoDBLocalClient(AWSCredentials awsCredentials) {
        return AmazonDynamoDBClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withEndpointConfiguration(
                        new AwsClientBuilder
                                .EndpointConfiguration(
                                        dynamodbLocalEndpoint,
                                        dynamodbLocalSigningRegion
                                )
                )
                .build();
    }
}
