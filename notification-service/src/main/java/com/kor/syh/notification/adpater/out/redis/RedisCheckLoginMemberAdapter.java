package com.kor.syh.notification.adpater.out.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.kor.syh.common.constant.RedisKey;
import com.kor.syh.notification.application.port.out.CheckLoginMemberPort;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class RedisCheckLoginMemberAdapter implements CheckLoginMemberPort {

	private final RedisTemplate<String, Object> redisTemplate;

	@Override
	public boolean isLoginMember(String memberId) {
		String key = RedisKey.LOGIN_PREFIX + memberId;
		return redisTemplate.hasKey(key) != null && redisTemplate.opsForHash().size(key) > 0;
	}
}
