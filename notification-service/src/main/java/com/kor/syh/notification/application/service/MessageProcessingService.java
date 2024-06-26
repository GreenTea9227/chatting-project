package com.kor.syh.notification.application.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.kor.syh.common.RedisPubSubNotification;
import com.kor.syh.notification.application.exception.FailSendSseEmitterException;
import com.kor.syh.notification.application.exception.NotFoundSseEmitterException;
import com.kor.syh.notification.application.port.in.notification.ReceiveNotificationUseCase;
import com.kor.syh.notification.application.port.in.notification.SendMessageCommand;
import com.kor.syh.notification.application.port.out.channel.MessagePublishPort;
import com.kor.syh.notification.application.port.out.channel.SendMessage;
import com.kor.syh.notification.application.port.out.notification.NotificationPersistencePort;
import com.kor.syh.notification.application.port.out.notification.SendNotificationUseCase;
import com.kor.syh.notification.domain.NotifyType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
	public void receive(String receiver, RedisPubSubNotification command) {

		SseEmitter sseEmitter = notificationPersistencePort.findById(receiver)
														   .orElseThrow(() -> new NotFoundSseEmitterException(
															   "SseEmitter가 존재하지 않습니다."));

		try {
			sseEmitter.send(SseEmitter.event()
									  .id(command.getId())
									  .name(command.getType().name())
									  .data(command.getContent()));
			log.info("send sseEmitter {}",command.getId());
		} catch (IOException e) {
			notificationPersistencePort.deleteById(receiver);
			throw new FailSendSseEmitterException(e.getMessage());
		}

	}
}
