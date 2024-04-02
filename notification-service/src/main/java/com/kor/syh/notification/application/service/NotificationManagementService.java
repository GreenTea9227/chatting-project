package com.kor.syh.notification.application.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.kor.syh.notification.application.port.in.notification.NotificationUseCase;
import com.kor.syh.notification.application.port.out.channel.MessageManagementPort;
import com.kor.syh.notification.application.port.out.notification.NotificationPersistencePort;
import com.kor.syh.notification.domain.Notify;
import com.kor.syh.notification.domain.NotifyType;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationManagementService implements NotificationUseCase {

	public static final String SUCCESS = "success";
	public static final String SERVER_ID = "server";

	private final NotificationPersistencePort notificationPersistencePort;
	private final MessageManagementPort messageManagementPort;

	@Override
	public SseEmitter createNotificationChannel(String memberId) {

		SseEmitter sseEmitter = new SseEmitter(60 * 1000L);

		notificationPersistencePort.save(memberId, sseEmitter);
		messageManagementPort.subscribe(memberId);

		sseEmitter.onTimeout(sseEmitter::complete);
		sseEmitter.onError((e) -> sseEmitter.complete());
		sseEmitter.onCompletion(() -> deleteNotificationChannel(memberId));

		sendDummyMessage(sseEmitter, memberId);
		return sseEmitter;
	}

	@Override
	public void deleteNotificationChannel(String memberId) {
		notificationPersistencePort.deleteById(memberId);
		messageManagementPort.removeSubscribe(memberId);
	}

	private void sendDummyMessage(SseEmitter sseEmitter, String memberId) {
		try {
			Notify notify = Notify.builder()
								  .id(UUID.randomUUID().toString())
								  .type(NotifyType.SUBSCRIBE)
								  .receiver(memberId)
								  .sender(SERVER_ID)
								  .content(SUCCESS)
								  .time(LocalDateTime.now())
								  .build();
			sseEmitter.send(SseEmitter.event()
									  .id(memberId)
									  .data(notify));
		} catch (IOException e) {
			deleteNotificationChannel(memberId);
			throw new RuntimeException(e);
		}
	}

}
