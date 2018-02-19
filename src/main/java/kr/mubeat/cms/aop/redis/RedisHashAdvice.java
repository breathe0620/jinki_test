package kr.mubeat.cms.aop.redis;

import kr.mubeat.cms.annotation.redis.RedisHash;
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
public class RedisHashAdvice {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private RedisTemplate redisTemplate;

    @Autowired
    public RedisHashAdvice(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Around("@annotation(redisHash)")
    public Object redisHash(
            ProceedingJoinPoint joinPoint,
            RedisHash redisHash
    ) throws Throwable {
        try {
            String hashKey = getHashKey(redisHash.hashKeyPosition(), joinPoint);
            Object object = redisTemplate.opsForHash().get(
                redisHash.key(),
                hashKey);

            if (object == null) {
                object = joinPoint.proceed();
            }
            if (object != null) {
                redisTemplate.opsForHash().put(
                    redisHash.key(),
                    hashKey,
                    object);
            }

            return object;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return joinPoint.proceed();
        }
    }

    private String getHashKey(int position, ProceedingJoinPoint joinPoint) {
        return String.valueOf(joinPoint.getArgs()[position]);
    }

}
