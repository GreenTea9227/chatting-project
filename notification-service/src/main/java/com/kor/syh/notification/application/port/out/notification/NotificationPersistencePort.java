package com.kor.syh.notification.application.port.out.notification;

import java.util.Optional;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface NotificationPersistencePort {
	void save(String memberId, SseEmitter sseEmitter);

	Optional<SseEmitter> findById(String memberId);

	void deleteById(String memberId);

}
