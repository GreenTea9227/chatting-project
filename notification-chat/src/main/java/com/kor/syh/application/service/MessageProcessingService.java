package com.kor.syh.application.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.kor.syh.application.port.in.notification.SendMessageCommand;
import com.kor.syh.application.port.out.notification.SendNotificationUseCase;
import com.kor.syh.application.port.in.notification.ReceiveNotificationUseCase;
import com.kor.syh.application.port.out.notification.NotificationPersistencePort;
import com.kor.syh.application.port.out.channel.MessagePublishPort;
import com.kor.syh.domain.Notify;
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
		Notify notify = Notify.builder()
							  .id(UUID.randomUUID().toString())
							  .type(NotifyType.NOTIFY)
							  .receiver(receiverId)
							  .sender(senderId)
							  .content(content)
							  .time(LocalDateTime.now())
							  .build();
		return messagePublishPort.publish(notify);
	}

	@Override
	public void receive(Notify notify) {
		String receiver = notify.getReceiver();
		String sender = notify.getSender();
		SseEmitter sseEmitter = notificationPersistencePort.findById(receiver).orElseThrow();

		try {
			sseEmitter.send(SseEmitter.event()
									  .id(notify.getId())
									  .name(notify.getType().name())
									  .data(notify));
		} catch (IOException e) {
			notificationPersistencePort.deleteById(receiver);
			throw new RuntimeException(e);
		}

	}
}
