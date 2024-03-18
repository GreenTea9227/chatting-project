package com.kor.syh.adpater.out.redis;

import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;

import com.kor.syh.adpater.in.redis.RedisSubscriber;
import com.kor.syh.application.port.out.redis.MessageManagementPort;
import com.kor.syh.util.TopicUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RedisMessageManageService implements MessageManagementPort {

	private final RedisMessageListenerContainer container;
	private final RedisSubscriber subscriber;

	@Override
	public void subscribe(String channelName) {
		String topic = TopicUtils.createTopic(channelName);
		container.addMessageListener(subscriber, ChannelTopic.of(topic));
	}

	@Override
	public void removeSubscribe(String channelName) {
		String topic = TopicUtils.createTopic(channelName);
		container.removeMessageListener(subscriber, ChannelTopic.of(topic));
	}

}
