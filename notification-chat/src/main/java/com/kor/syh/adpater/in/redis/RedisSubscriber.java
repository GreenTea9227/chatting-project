package com.kor.syh.adpater.in.redis;

import java.nio.charset.StandardCharsets;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import com.kor.syh.application.port.in.notification.ReceiveNotificationUseCase;
import com.kor.syh.util.TopicUtils;
import com.kor.syh.utils.JsonUtil;

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
		ReceiveMessage receiveMessage = JsonUtil.byteToClass(message.getBody(), ReceiveMessage.class);

		receiveNotification.receive(receiver,receiveMessage);
	}
}
