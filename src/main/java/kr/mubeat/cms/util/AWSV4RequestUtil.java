package kr.mubeat.cms.util;

import kr.mubeat.cms.enumerations.AWSServices;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by moonkyu.lee on 2017. 7. 26..
 */
@Service
public class AWSV4RequestUtil {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${spring.aws.region}")
    private String region;

    @Value("${spring.aws.access-key}")
    private String accessKey;

    @Value("${spring.aws.secret-key}")
    private String secretKey;

    /**
     * Responsehandler
     */
    ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
        @Override
        public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
                return entity != null ? EntityUtils.toString(entity) : null;
            } else {
                throw new ClientProtocolException("Unexpected response status : " + status);
            }
        }
    };

//    public String request(RequestMethod requestMethod, AWSServices service, String host, String queryString, String payload) {
//        return this.request(requestMethod, service, host, queryString, payload, null);
//    }

    public String request(RequestMethod requestMethod, AWSServices service, String host, String queryString, String payload) {
        TreeMap<String, String> awsHeaders = new TreeMap<String, String>();
        awsHeaders.put("host", host);
//        if (service.equals(AWSServices.dynamodb)) {
//            awsHeaders.put("x-amz-target", target);
//            awsHeaders.put("content-length", String.valueOf(payload.length()));
//            awsHeaders.put("content-type", "application/x-www-form-urlencoded");
//        }

        String url = "https://" + host + queryString;

        AWSV4Auth awsv4Auth = new AWSV4Auth.Builder(accessKey, secretKey)
                .regionName(region)
                .serviceName(service.name())
                .httpMethodName(requestMethod.name())
                .canonicalURI(queryString)
                .queryParametes(null)
                .awsHeaders(awsHeaders)
                .payload(payload)
                .build();

        if (requestMethod.equals(RequestMethod.GET)) {
            HttpGet httpGet = new HttpGet(url);

            Map<String, String> header = awsv4Auth.getHeaders();
            for (Map.Entry<String, String> entrySet : header.entrySet()) {
                String key = entrySet.getKey();
                String value = entrySet.getValue();

                httpGet.addHeader(key, value);
            }
            return httpCustomRequest(httpGet);
        } else if (requestMethod.equals(RequestMethod.POST)) {
            HttpPost httpPost = new HttpPost(url);
            StringEntity requestEntity = new StringEntity(payload, ContentType.APPLICATION_JSON);
            httpPost.setEntity(requestEntity);

            Map<String, String> header = awsv4Auth.getHeaders();
            for (Map.Entry<String, String> entrySet : header.entrySet()) {
                String key = entrySet.getKey();
                String value = entrySet.getValue();

                httpPost.addHeader(key, value);
            }
            return httpCustomRequest(httpPost);
        } else if (requestMethod.equals(RequestMethod.PUT)) {
            HttpPut httpPut = new HttpPut(url);
            StringEntity requestEntity = new StringEntity(payload, ContentType.APPLICATION_JSON);
            httpPut.setEntity(requestEntity);

            Map<String, String> header = awsv4Auth.getHeaders();
            for (Map.Entry<String, String> entrySet : header.entrySet()) {
                String key = entrySet.getKey();
                String value = entrySet.getValue();

                httpPut.addHeader(key, value);
            }
            return httpCustomRequest(httpPut);
        } else if (requestMethod.equals(RequestMethod.DELETE)) {
            HttpDelete httpDelete = new HttpDelete(url);

            Map<String, String> header = awsv4Auth.getHeaders();
            for (Map.Entry<String, String> entrySet : header.entrySet()) {
                String key = entrySet.getKey();
                String value = entrySet.getValue();

                httpDelete.addHeader(key, value);
            }
            return httpCustomRequest(httpDelete);
        }
        return null;
    }

    private String httpGetRequest(HttpGet httpGet) {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {
            String response = httpClient.execute(httpGet, responseHandler);
            httpClient.close();
            return response;
        } catch (Exception e) {}
        return null;
    }

    private String httpPostRequest(HttpPost httpPost) {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {
            String response = httpClient.execute(httpPost, responseHandler);
            httpClient.close();
            return response;
        } catch (Exception e) {}
        return null;
    }

    private String httpPutRequest(HttpPut httpPut) {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {
            String response = httpClient.execute(httpPut, responseHandler);
            httpClient.close();
            return response;
        } catch (Exception e) {}
        return null;
    }

    private String httpDeleteRequest(HttpDelete httpDelete) {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {
            String response = httpClient.execute(httpDelete, responseHandler);
            httpClient.close();
            return response;
        } catch (Exception e) {}
        return null;
    }

    private String httpCustomRequest(HttpUriRequest request) {

        CloseableHttpClient httpClient = HttpClientBuilder.create().setSSLContext(getSSLContext()).build();

        try {
            String response = httpClient.execute(request, responseHandler);
            httpClient.close();
            return response;
        } catch (Exception e) {
            try {
                httpClient.close();
            } catch(Exception e1) {
                logger.error(e1.getMessage());
            }
            logger.error(e.getMessage());
        }
        return null;
    }

    private SSLContext getSSLContext() {
        SSLContext context = null;
        try {
            context = SSLContext.getInstance("TLSv1");
            context.init(null, null, null);
        } catch(Exception e) {
            logger.error(e.getMessage());
        }

        return context;
    }


}
