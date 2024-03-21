package com.kor.syh.notification.adpater.in.channel;

import java.nio.charset.StandardCharsets;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import com.kor.syh.common.PublishNotificationDto;
import com.kor.syh.notification.application.port.in.notification.ReceiveNotificationUseCase;
import com.kor.syh.notification.util.TopicUtils;
import com.kor.syh.common.utils.JsonUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisSubscriber implements MessageListener {

	private final ReceiveNotificationUseCase receiveNotification;

	@Override
	public void onMessage(Message message, byte[] pattern) {

		String fullTopicName = new String(message.getChannel(), StandardCharsets.UTF_8);
		String receiver = TopicUtils.extractTopic(fullTopicName);
		PublishNotificationDto receiveMessage = JsonUtil.byteToClass(message.getBody(), PublishNotificationDto.class);

		receiveNotification.receive(receiver, receiveMessage);
	}
}
