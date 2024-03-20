package com.kor.syh.notification.adpater.out.redis;

import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;

import com.kor.syh.notification.adpater.in.channel.RedisSubscriber;
import com.kor.syh.notification.application.port.out.channel.MessageManagementPort;
import com.kor.syh.notification.util.TopicUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RedisMessageManageAdapter implements MessageManagementPort {

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
