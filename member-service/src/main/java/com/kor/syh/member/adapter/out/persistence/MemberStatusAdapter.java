package com.kor.syh.member.adapter.out.persistence;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.kor.syh.common.constant.RedisKey;
import com.kor.syh.member.application.port.out.member.LoginStatusPort;
import com.kor.syh.member.application.port.out.member.LogoutStatusPort;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class MemberStatusAdapter implements LoginStatusPort, LogoutStatusPort {

	private final RedisTemplate<String, Object> redisTemplate;


	@Value("${jwt.token_validate_time}")
	private Long loginTimeout;

	@Override
	public void login(String userId, String clientIp) {
		String key = RedisKey.LOGIN_PREFIX + userId;
		redisTemplate.opsForHash().put(key, clientIp, System.currentTimeMillis() + loginTimeout);
		redisTemplate.expire(key,loginTimeout, TimeUnit.MILLISECONDS);
	}

	@Override
	public void logout(String userId, String clientIp) {
		redisTemplate.opsForHash().delete(RedisKey.LOGIN_PREFIX + userId, clientIp);
	}
}
