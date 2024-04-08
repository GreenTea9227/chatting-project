package com.kor.syh.notification.application.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.kor.syh.notification.application.exception.NotificationDeletionException;
import com.kor.syh.notification.application.exception.UnauthorizedAccessException;
import com.kor.syh.notification.application.port.in.notification.NotificationUseCase;
import com.kor.syh.notification.application.port.out.CheckLoginMemberPort;
import com.kor.syh.notification.application.port.out.channel.MessageManagementPort;
import com.kor.syh.notification.application.port.out.notification.NotificationPersistencePort;
import com.kor.syh.notification.domain.Notify;
import com.kor.syh.notification.domain.NotifyType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationManagementService implements NotificationUseCase {

	public static final String SUCCESS = "success";
	public static final String SERVER_ID = "server";

	private final NotificationPersistencePort notificationPersistencePort;
	private final MessageManagementPort messageManagementPort;
	private final CheckLoginMemberPort checkLoginMemberPort;

	@Override
	public SseEmitter createNotificationChannel(String memberId) {

		if (!checkLoginMemberPort.isLoginMember(memberId)) {
			throw new UnauthorizedAccessException("먼저 로그인을 해야 합니다.");
		};

		SseEmitter sseEmitter = new SseEmitter(60 * 1000L);

		notificationPersistencePort.save(memberId, sseEmitter);
		messageManagementPort.subscribe(memberId);

		sseEmitter.onTimeout(sseEmitter::complete);
		sseEmitter.onError((e) -> sseEmitter.complete());
		sseEmitter.onCompletion(() -> deleteNotificationChannel(memberId));

		sendDummyMessage(sseEmitter, memberId);
		log.info("create sseEmitter - memberId : [{}]",memberId);
		return sseEmitter;
	}

	@Override
	public void deleteNotificationChannel(String memberId)  {
		try {
			notificationPersistencePort.deleteById(memberId);
			messageManagementPort.removeSubscribe(memberId);
		} catch (Exception e) {
			log.error("delete notification channel error - memberId : [{}]",memberId);
			throw new NotificationDeletionException("Failed to delete notification channel", e);
		}
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
