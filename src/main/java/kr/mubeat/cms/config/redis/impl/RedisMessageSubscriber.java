package kr.mubeat.cms.config.redis.impl;

import kr.mubeat.cms.dto.manager.analysis.SocketMessage;
import kr.mubeat.cms.enumerations.RedisStoreNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by moonkyu.lee on 2017. 8. 25..
 */
@Service
public class RedisMessageSubscriber implements MessageListener {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private RedisTemplate redisTemplate;
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public RedisMessageSubscriber(
        RedisTemplate redisTemplate,
        SimpMessagingTemplate simpMessagingTemplate
    ) {
        this.redisTemplate = redisTemplate;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String received = new String(message.toString());
        simpMessagingTemplate.convertAndSend("/spark/status", new SocketMessage(received));

        logger.info("Message received : " + received);
        if (received.indexOf("[Completed") != -1) {

            /**
             * Spark Job 처리시
             * [completed:jobtype,startDate] 형태로 기록 해야 함
             */
            String type = received.substring(received.indexOf(":") + 1, received.indexOf(","));
            String startDate = received.substring(received.indexOf(",") + 1, received.indexOf("]"));
            // TODO 작업 완료분 처리
            redisTemplate.setHashValueSerializer(new StringRedisSerializer());
            String result = String.valueOf(
                redisTemplate.opsForHash().get(RedisStoreNames.SPARK_RESULT.getStoreType()+":"+type, startDate)
            );
            simpMessagingTemplate.convertAndSend("/spark/data", new SocketMessage(result));
            logger.info("result\n" +
                result
            );
        }
    }
}
