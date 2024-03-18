package com.kor.syh.application.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.kor.syh.adpater.in.channel.ReceiveMessage;
import com.kor.syh.application.port.in.notification.ReceiveNotificationUseCase;
import com.kor.syh.application.port.in.notification.SendMessageCommand;
import com.kor.syh.application.port.out.channel.MessagePublishPort;
import com.kor.syh.application.port.out.channel.SendMessage;
import com.kor.syh.application.port.out.notification.NotificationPersistencePort;
import com.kor.syh.application.port.out.notification.SendNotificationUseCase;
import com.kor.syh.domain.NotifyType;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageProcessingService implements SendNotificationUseCase, ReceiveNotificationUseCase {

	private final NotificationPersistencePort notificationPersistencePort;
	private final MessagePublishPort messagePublishPort;

	@Override
	public Long send(SendMessageCommand command) {
		String receiverId = command.getReceiverId();
		String senderId = command.getSenderId();
		String content = command.getContent();

		SendMessage message = SendMessage.builder()
										 .id(UUID.randomUUID().toString())
										 .type(NotifyType.NOTIFY)
										 .sender(senderId)
										 .content(content)
										 .time(LocalDateTime.now())
										 .build();

		return messagePublishPort.publish(receiverId, message);
	}

	@Override
	public void receive(String receiver, ReceiveMessage command) {

		SseEmitter sseEmitter = notificationPersistencePort.findById(receiver).orElseThrow();

		try {
			sseEmitter.send(SseEmitter.event()
									  .id(command.getId())
									  .name(command.getType().name())
									  .data(command));
		} catch (IOException e) {
			notificationPersistencePort.deleteById(receiver);
			throw new RuntimeException(e);
		}

	}
}
