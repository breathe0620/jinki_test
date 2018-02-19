package kr.mubeat.cms.aop.redis;

import kr.mubeat.cms.annotation.redis.RedisHashMapExpire;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by moonkyu.lee on 2017. 9. 4..
 */
@Aspect
@Component
public class RedisHashMapExpireAdvice {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private RedisTemplate redisTemplate;

    @Autowired
    public RedisHashMapExpireAdvice(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Around("@annotation(redisHashMapExpire)")
    public Object redisHashMapExpire(
            ProceedingJoinPoint joinPoint,
            RedisHashMapExpire redisHashMapExpire
    ) throws Throwable {
        try {
            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
            String hashKey = getHashKey(redisHashMapExpire.hashKeyPosition(), joinPoint);
            Object object = redisTemplate.opsForHash().get(
                    redisHashMapExpire.key(),
                    hashKey
            );

            if (object == null) {
                object = joinPoint.proceed();
                if (object != null) {
                    redisTemplate.opsForHash().put(redisHashMapExpire.key(), hashKey, object);
                    redisTemplate.expire(redisHashMapExpire.key(), redisHashMapExpire.expireTime(), redisHashMapExpire.timeUnit());
                }
            }
            if (object != null) {
                response.setHeader(redisHashMapExpire.key(), String.valueOf(redisTemplate.getExpire(redisHashMapExpire.key())));
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
