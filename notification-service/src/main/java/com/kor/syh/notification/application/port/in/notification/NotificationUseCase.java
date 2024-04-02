package com.kor.syh.notification.application.port.in.notification;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface NotificationUseCase {
	SseEmitter createNotificationChannel(String memberId);

	void deleteNotificationChannel(String memberId);
}
