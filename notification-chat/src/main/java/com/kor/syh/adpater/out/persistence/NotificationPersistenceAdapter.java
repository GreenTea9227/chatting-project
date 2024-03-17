package com.kor.syh.adpater.out.persistence;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.kor.syh.application.port.out.persistence.NotificationChannelPort;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class NotificationPersistenceAdapter implements NotificationChannelPort {
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