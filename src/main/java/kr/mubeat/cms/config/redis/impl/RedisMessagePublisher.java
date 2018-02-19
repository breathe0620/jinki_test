package kr.mubeat.cms.config.redis.impl;

import kr.mubeat.cms.config.redis.MessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

/**
 * Created by moonkyu.lee on 2017. 8. 25..
 */
@Service
public class RedisMessagePublisher implements MessagePublisher {

    private RedisTemplate redisTemplate;
    private ChannelTopic channelTopic;

    @Autowired
    public RedisMessagePublisher(
            RedisTemplate redisTemplate,
            ChannelTopic channelTopic
    ) {
        this.redisTemplate = redisTemplate;
        this.channelTopic = channelTopic;
    }

    @Override
    public void publish(String message) {
        redisTemplate.convertAndSend(channelTopic.getTopic(), message);
    }
}
