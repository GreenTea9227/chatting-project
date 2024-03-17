package com.kor.syh.application.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.kor.syh.application.port.in.notification.NotificationUseCase;
import com.kor.syh.application.port.out.persistence.NotificationChannelPort;
import com.kor.syh.application.port.out.redis.MessageManagementPort;
import com.kor.syh.domain.Notify;
import com.kor.syh.domain.NotifyType;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationManagementService implements NotificationUseCase {

	public static final String SUCCESS = "success";
	public static final String SERVER_ID = "server";

	private final NotificationChannelPort notificationChannelPort;
	private final MessageManagementPort messageManagementPort;

	@Override
	public SseEmitter createNotification(String memberId) {

		SseEmitter sseEmitter = new SseEmitter(60 * 1000L);

		notificationChannelPort.save(memberId, sseEmitter);
		messageManagementPort.subscribe(memberId);

		sseEmitter.onTimeout(sseEmitter::complete);
		sseEmitter.onError((e) -> sseEmitter.complete());
		sseEmitter.onCompletion(() -> {
			deleteNotification(memberId);
		});

		sendDummyMessage(sseEmitter, memberId);
		return sseEmitter;
	}

	@Override
	public void deleteNotification(String memberId) {
		notificationChannelPort.deleteById(memberId);
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
			deleteNotification(memberId);
			throw new RuntimeException(e);
		}
	}

}
