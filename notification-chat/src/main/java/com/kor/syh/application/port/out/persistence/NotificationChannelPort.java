package com.kor.syh.application.port.out.persistence;

import java.util.Optional;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface NotificationChannelPort {
	void save(String memberId, SseEmitter sseEmitter);

	Optional<SseEmitter> findById(String memberId);

	void deleteById(String memberId);

}
