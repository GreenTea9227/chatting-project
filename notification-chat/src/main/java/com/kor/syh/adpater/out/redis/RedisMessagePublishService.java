package com.kor.syh.adpater.out.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.kor.syh.application.port.out.redis.MessagePublishPort;
import com.kor.syh.domain.Notify;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RedisMessagePublishService implements MessagePublishPort {

	private final RedisTemplate<String, Object> redisTemplate;

	@Override
	public Long publish(Notify notify) {
		String receiver = notify.getReceiver();
		return redisTemplate.convertAndSend(receiver, notify);
	}
}
