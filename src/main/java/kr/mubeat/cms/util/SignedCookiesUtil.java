package kr.mubeat.cms.util;

import kr.mubeat.cms.dto.SignedCookie;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by moonkyu.lee on 2017. 8. 29..
 *
 * 참고 자료
 * http://docs.aws.amazon.com/AWSJavaSDK/latest/javadoc/com/amazonaws/services/cloudfront/CloudFrontUrlSigner.html
 * http://docs.aws.amazon.com/ko_kr/AmazonCloudFront/latest/DeveloperGuide/CFPrivateDistJavaDevelopment.html
 */
@Service
public class SignedCookiesUtil {

    @Value("${api.url}")
    private String apiURL;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

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

    public SignedCookie getSignedCookies(
            Long id
    ) {
        HttpGet httpGet = new HttpGet(apiURL+id+"/**");
//        HttpGet httpGet = new HttpGet("https://dev.api.mubeat.tv:3000/get-signed-cookies");
        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {
            String response = httpClient.execute(httpGet, responseHandler);
            httpClient.close();

            JSONObject jsonObject = new JSONObject(response);
            SignedCookie signedCookie = new SignedCookie();
            signedCookie.setPolicy(String.valueOf(jsonObject.get("CloudFront-Policy")));
            signedCookie.setSignature(String.valueOf(jsonObject.get("CloudFront-Signature")));
            signedCookie.setKeyPairId(String.valueOf(jsonObject.get("CloudFront-Key-Pair-Id")));
            return signedCookie;
        } catch (Exception e) {}

        return null;
    }
}
