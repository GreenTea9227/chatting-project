package com.kor.syh.chat.adapter.out;

import java.util.UUID;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.kor.syh.chat.application.port.out.SendNotificationPort;
import com.kor.syh.common.NotifyType;
import com.kor.syh.common.PublishNotificationDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class SendNotificationAdapter implements SendNotificationPort {

	private final RedisTemplate<String, Object> redisTemplate;

	@Override
	public void sendNotification(String fromId, String content) {
		PublishNotificationDto build = PublishNotificationDto.builder()
															 .id(UUID.randomUUID().toString())
															 .content(content)
															 .senderId(fromId)
															 .type(NotifyType.MULTIPLE)
															 .build();

	}
}
