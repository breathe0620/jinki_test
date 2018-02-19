package kr.mubeat.cms.config;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;

/**
 * Created by doohwan.yoo on 2017. 5. 22..
 */
//@Configuration
public class EsConfig {

    @Value("${spring.elasticsearch.cluster-name}")
    private String clusterName;

    @Value("${spring.elasticsearch.host}")
    private String host;

    @Value("${spring.elasticsearch.port}")
    private int port;

//    @Bean
//    public Client client() throws Exception {
//        Settings settings = Settings.builder()
//                .put("cluster.name", clusterName).build();
//
//        return new PreBuiltTransportClient(settings)
//                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), port));
//
//    }
}
