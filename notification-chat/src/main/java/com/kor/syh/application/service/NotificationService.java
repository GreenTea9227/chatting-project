package com.kor.syh.application.service;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.kor.syh.application.port.in.NotificationUseCase;
import com.kor.syh.application.port.in.SendNotificationUseCase;
import com.kor.syh.application.port.out.persistence.NotificationChannelPort;
import com.kor.syh.domain.Chat;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService implements NotificationUseCase, SendNotificationUseCase {

	public static final String SUCCESS = "success";
	public static final String SERVER_ID = "server";
	private final NotificationChannelPort notificationChannelPort;

	@Override
	public SseEmitter createNotification(String memberId) {
		SseEmitter sseEmitter = new SseEmitter();
		sseEmitter.onTimeout(sseEmitter::complete);
		sseEmitter.onError((e) -> sseEmitter.complete());
		sseEmitter.onCompletion(() -> notificationChannelPort.deleteById(memberId));

		send(SERVER_ID, memberId, SUCCESS);

		notificationChannelPort.save(memberId, sseEmitter);
		return sseEmitter;
	}

	@Override
	public void deleteNotification(String memberId) {
		notificationChannelPort.deleteById(memberId);
	}

	@Override
	public void send(String senderId, String receiverId, String content) {
		SseEmitter sseEmitter = notificationChannelPort.findById(receiverId).orElseThrow();
		try {
			sseEmitter.send(SseEmitter.event()
									  .id(senderId)
									  .data(Chat.builder()
												.content(content)
												.build(), MediaType.APPLICATION_JSON));

		} catch (IOException e) {
			notificationChannelPort.deleteById(receiverId);
			throw new RuntimeException(e);
		}
	}
}
