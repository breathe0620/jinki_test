package kr.mubeat.cms.aop.redis;

import kr.mubeat.cms.annotation.redis.RedisHashExpire;
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
 * Created by moonkyu.lee on 2017. 7. 5..
 */
@Aspect
@Component
public class RedisHashExpireAdvice {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private RedisTemplate redisTemplate;

    @Autowired
    public RedisHashExpireAdvice(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Around("@annotation(redisHashExpire)")
    public Object redisHashExpire(
            ProceedingJoinPoint joinPoint,
            RedisHashExpire redisHashExpire
    ) throws Throwable {
        try {
            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
            String hashKey = getHashKey(redisHashExpire.hashKeyPosition(), joinPoint);
            Object object = redisTemplate.opsForHash().get(
                redisHashExpire.key(),
                hashKey
            );

            if (object == null) {
                object = joinPoint.proceed();
                if (object != null) {
                    redisTemplate.opsForHash().put(redisHashExpire.key(), hashKey, object);
                    redisTemplate.expire(redisHashExpire.key(), redisHashExpire.expireTime(), redisHashExpire.timeUnit());
                }
            }
            if (object != null) {
                response.setHeader(redisHashExpire.key(), String.valueOf(redisTemplate.getExpire(redisHashExpire.key())));
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
