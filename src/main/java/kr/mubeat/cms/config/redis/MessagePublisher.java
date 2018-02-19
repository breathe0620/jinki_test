package kr.mubeat.cms.config.redis;

/**
 * Created by moonkyu.lee on 2017. 8. 25..
 */
public interface MessagePublisher {
    void publish(String message);
}
