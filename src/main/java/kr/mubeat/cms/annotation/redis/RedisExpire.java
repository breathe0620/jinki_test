package kr.mubeat.cms.annotation.redis;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * Created by moonkyu.lee on 2017. 7. 5..
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RedisExpire {

    String key();
    long expireTime() default 0L;
    TimeUnit timeUnit() default TimeUnit.MINUTES;

}
