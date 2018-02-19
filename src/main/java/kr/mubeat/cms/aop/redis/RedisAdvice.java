package kr.mubeat.cms.aop.redis;

import kr.mubeat.cms.annotation.redis.Redis;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by moonkyu.lee on 2017. 6. 30..
 */
@Aspect
@Component
public class RedisAdvice {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private RedisTemplate redisTemplate;

    @Autowired
    public RedisAdvice(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Around("@annotation(redis)")
    public Object redis(
            ProceedingJoinPoint joinPoint,
            Redis redis
    ) throws Throwable {
        try {
            Object object = redisTemplate.opsForValue().get(redis.key());

            if (object == null) {
                object = joinPoint.proceed();
            }
            if (object != null) {
                redisTemplate.opsForValue().set(redis.key(), object);
            }

            return object;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return joinPoint.proceed();
        }
    }

}
