package com.kor.syh.notification.adpater.out.persistence;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.kor.syh.notification.application.port.out.notification.NotificationPersistencePort;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class NotificationPersistenceAdapter implements NotificationPersistencePort {
	private final Map<String, SseEmitter> emitterMap = new ConcurrentHashMap<>();

	@Override
	public void save(String memberId, SseEmitter sseEmitter) {
		emitterMap.put(memberId, sseEmitter);
	}

	@Override
	public Optional<SseEmitter> findById(String memberId) {
		return Optional.ofNullable(emitterMap.get(memberId));
	}

	@Override
	public void deleteById(String memberId) {
		emitterMap.remove(memberId);
	}
}
