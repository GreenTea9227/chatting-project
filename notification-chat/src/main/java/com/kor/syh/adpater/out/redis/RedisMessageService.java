package com.kor.syh.adpater.out.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;

import com.kor.syh.adpater.in.redis.RedisSubscriber;
import com.kor.syh.application.port.out.redis.MessagePublishPort;
import com.kor.syh.domain.Notify;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RedisMessageService implements MessagePublishPort {

	private final RedisMessageListenerContainer container;
	private final RedisTemplate<String, Object> redisTemplate;
	private final RedisSubscriber subscriber;

	public void subscribe(String channelName) {
		container.addMessageListener(subscriber, ChannelTopic.of(channelName));
	}

	public Long publish(Notify notify) {
		String receiver = notify.getReceiver();
		return redisTemplate.convertAndSend(receiver, notify);
	}

	public void removeSubscribe(String channelName) {
		container.removeMessageListener(subscriber, ChannelTopic.of(channelName));
	}
}
