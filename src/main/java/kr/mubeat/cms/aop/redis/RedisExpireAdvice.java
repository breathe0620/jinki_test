package kr.mubeat.cms.aop.redis;

import kr.mubeat.cms.annotation.redis.RedisExpire;
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
public class RedisExpireAdvice {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private RedisTemplate redisTemplate;

    @Autowired
    public RedisExpireAdvice(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Around("@annotation(redisExpire)")
    public Object redisExpire(
            ProceedingJoinPoint joinPoint,
            RedisExpire redisExpire
    ) throws Throwable {
        try {
            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
            Object object = redisTemplate.opsForValue().get(redisExpire.key());

            if (object == null) {
                object = joinPoint.proceed();
                if (object != null) {
                    redisTemplate.opsForValue().set(redisExpire.key(), object, redisExpire.expireTime(), redisExpire.timeUnit());
                }
            }
            if (object != null) {
                response.setHeader(redisExpire.key(), String.valueOf(redisTemplate.getExpire(redisExpire.key())));
            }

            return object;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return joinPoint.proceed();
        }
    }
}
