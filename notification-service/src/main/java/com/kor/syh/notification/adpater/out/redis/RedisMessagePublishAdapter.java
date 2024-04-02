package com.kor.syh.notification.adpater.out.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.kor.syh.notification.application.port.out.channel.MessagePublishPort;
import com.kor.syh.notification.application.port.out.channel.SendMessage;
import com.kor.syh.notification.util.TopicUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RedisMessagePublishAdapter implements MessagePublishPort {

	private final RedisTemplate<String, Object> redisTemplate;

	@Override
	public Long publish(String receiver, SendMessage message) {
		String topic = TopicUtils.createTopic(receiver);
		return redisTemplate.convertAndSend(topic, message);
	}
}
