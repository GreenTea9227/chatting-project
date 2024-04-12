package com.kor.syh.adapter.out;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.kor.syh.application.port.out.LoginStatusPort;
import com.kor.syh.common.constant.RedisKey;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class RedisRepository implements LoginStatusPort {
	private final RedisTemplate<String, Object> redisTemplate;

	@Override
	public boolean isLoginMember(String userId) {
		String key = RedisKey.LOGIN_PREFIX + userId;
		Long hashSize = redisTemplate.opsForHash().size(key);
		return hashSize > 0;
	}
}
