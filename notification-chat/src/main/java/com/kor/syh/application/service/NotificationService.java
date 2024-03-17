package com.kor.syh.application.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.kor.syh.application.port.in.notification.MessageType;
import com.kor.syh.application.port.in.notification.NotificationUseCase;
import com.kor.syh.application.port.in.notification.SendMessageCommand;
import com.kor.syh.application.port.in.notification.SendNotificationUseCase;
import com.kor.syh.application.port.out.notification.ReceiveNotification;
import com.kor.syh.application.port.out.persistence.NotificationChannelPort;
import com.kor.syh.application.port.out.redis.MessagePublishPort;
import com.kor.syh.domain.Notify;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService implements NotificationUseCase, SendNotificationUseCase, ReceiveNotification {

	public static final String SUCCESS = "success";
	public static final String SERVER_ID = "server";
	private final NotificationChannelPort notificationChannelPort;
	private final MessagePublishPort messagePublishPort;

	@Override
	public SseEmitter createNotification(String memberId) {

		SseEmitter sseEmitter = new SseEmitter();
		sseEmitter.onTimeout(sseEmitter::complete);
		sseEmitter.onError((e) -> sseEmitter.complete());
		sseEmitter.onCompletion(() -> notificationChannelPort.deleteById(memberId));

		send(SendMessageCommand.of(MessageType.SERVER, SERVER_ID, memberId, SUCCESS));

		notificationChannelPort.save(memberId, sseEmitter);
		messagePublishPort.subscribe(memberId);

		return sseEmitter;
	}

	@Override
	public void deleteNotification(String memberId) {
		notificationChannelPort.deleteById(memberId);
		messagePublishPort.removeSubscribe(memberId);
	}

	@Override
	public void send(SendMessageCommand command) {
		String receiverId = command.getReceiverId();
		String senderId = command.getSenderId();
		String content = command.getContent();
		Notify notify = Notify.builder()
							  .id(UUID.randomUUID().toString())
							  .receiver(receiverId)
							  .sender(senderId)
							  .content(content)
							  .time(LocalDateTime.now())
							  .build();
		messagePublishPort.publish(notify);
	}

	@Override
	public void receive(Notify notify) {
		String receiver = notify.getReceiver();
		String sender = notify.getSender();
		SseEmitter sseEmitter = notificationChannelPort.findById(receiver).orElseThrow();
		try {

			sseEmitter.send(SseEmitter.event()
									  .id(receiver)
									  .data(sender), MediaType.APPLICATION_JSON);
		} catch (IOException e) {
			deleteNotification(receiver);
			throw new RuntimeException(e);
		}
	}
}
