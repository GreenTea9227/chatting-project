package com.kor.syh.member.adapter.out.persistence;

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

	@Value("${login.timeout}")
	private Long loginTimeout;

	@Override
	public void login(String userId, String clientIp) {
		redisTemplate.opsForHash().put(RedisKey.LOGIN_PREFIX + userId, clientIp, System.currentTimeMillis() + loginTimeout);
	}

	@Override
	public void logout(String userId, String clientIp) {
		redisTemplate.opsForHash().delete(RedisKey.LOGIN_PREFIX + userId, clientIp);
	}
}
