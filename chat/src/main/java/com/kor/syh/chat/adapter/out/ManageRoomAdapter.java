package com.kor.syh.chat.adapter.out;

import static com.kor.syh.common.constant.RedisKey.*;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.kor.syh.chat.application.port.out.ManageRoomParticipantPort;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class ManageRoomAdapter implements ManageRoomParticipantPort {

	private final RedisTemplate<String, Object> redisTemplate;

	@Override
	public boolean isRoomParticipant(String roomId, String userId) {
		Boolean isMember = redisTemplate.opsForSet().isMember(ROOM_PREFIX + roomId, userId);
		return isMember != null && isMember;
	}

	@Override
	public boolean isChatRoomExists(String roomId) {
		Boolean exists = redisTemplate.hasKey(ROOM_PREFIX + roomId);
		return exists != null && exists;
	}
}
