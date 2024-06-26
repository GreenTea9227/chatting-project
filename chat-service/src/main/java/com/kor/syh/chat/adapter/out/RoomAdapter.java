package com.kor.syh.chat.adapter.out;

import static com.kor.syh.common.constant.RedisKey.*;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.kor.syh.chat.application.port.out.RoomCachePort;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class RoomAdapter implements RoomCachePort {

	private final RedisTemplate<String, Object> redisTemplate;

	@Override
	public void createRoom(String roomId, String userId) {
		redisTemplate.opsForHash().put(ROOM_PREFIX + roomId, userId, "0");
	}

	@Override
	public void enterRoom(String roomId, String userId) {
		redisTemplate.opsForHash().put(ROOM_PREFIX + roomId, userId, "1");
	}

	@Override
	public void exit(String roomId, String userId) {
		redisTemplate.opsForHash().put(ROOM_PREFIX + roomId, userId, 0);
	}

	@Override
	public boolean isRoomParticipant(String roomId, String userId) {
		return redisTemplate.opsForHash().hasKey(ROOM_PREFIX + roomId, userId);
	}

	@Override
	public boolean isParticipatingNow(String roomId, String userId) {
		String fieldValue = (String)redisTemplate.opsForHash().get(ROOM_PREFIX + roomId, userId);
		return fieldValue != null && !fieldValue.equals("0");
	}

	@Override
	public boolean isChatRoomExists(String roomId) {
		Boolean exists = redisTemplate.hasKey(ROOM_PREFIX + roomId);
		return exists != null && exists;
	}
}
