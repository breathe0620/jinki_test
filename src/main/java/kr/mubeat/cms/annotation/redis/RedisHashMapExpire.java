package kr.mubeat.cms.annotation.redis;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * Created by moonkyu.lee on 2017. 9. 4..
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RedisHashMapExpire {

    String key();

    /**
     * 0부터 시작, hashKey 를 위하여 반드시 필요
     * @return
     */
    int hashKeyPosition() default 0;
    long expireTime() default 0L;
    TimeUnit timeUnit() default TimeUnit.MINUTES;
}
