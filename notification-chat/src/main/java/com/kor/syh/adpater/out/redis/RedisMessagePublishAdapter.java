package com.kor.syh.adpater.out.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.kor.syh.application.port.out.channel.MessagePublishPort;
import com.kor.syh.application.port.out.channel.SendMessage;
import com.kor.syh.domain.Notify;
import com.kor.syh.util.TopicUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RedisMessagePublishAdapter implements MessagePublishPort {

	private final RedisTemplate<String, Object> redisTemplate;

	@Override
	public Long publish(String receiver,SendMessage message) {
		String topic = TopicUtils.createTopic(receiver);
		return redisTemplate.convertAndSend(topic, message);
	}
}
