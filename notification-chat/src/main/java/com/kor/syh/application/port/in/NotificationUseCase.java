package com.kor.syh.application.port.in;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface NotificationUseCase {
	SseEmitter createNotification(String memberId);

	void deleteNotification(String memberId);
}
