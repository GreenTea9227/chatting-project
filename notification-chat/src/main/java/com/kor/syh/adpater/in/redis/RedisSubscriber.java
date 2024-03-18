package com.kor.syh.adpater.in.redis;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kor.syh.application.port.out.notification.ReceiveNotification;
import com.kor.syh.domain.Notify;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisSubscriber implements MessageListener {

	private final ObjectMapper objectMapper;
	private final ReceiveNotification receiveNotification;

	@Override
	public void onMessage(Message message, byte[] pattern) {

		try {
			String fullTopicName = new String(message.getChannel(), StandardCharsets.UTF_8);
			Notify notify = objectMapper.readValue(message.getBody(), Notify.class);
			receiveNotification.receive(notify);
		} catch (IOException e) {
			log.error("message error", e);
			throw new RuntimeException(e);
		}
	}
}
