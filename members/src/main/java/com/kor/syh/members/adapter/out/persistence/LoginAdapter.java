package com.kor.syh.members.adapter.out.persistence;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.kor.syh.common.constant.RedisKey;
import com.kor.syh.members.application.port.out.member.LoginMemberPort;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class LoginAdapter implements LoginMemberPort {

	private final RedisTemplate<String, Object> redisTemplate;

	@Value("${login.timeout}")
	private Long loginTimeout;

	@Override
	public void login(String userId, String ip) {
		redisTemplate.opsForHash().put(RedisKey.LOGIN_PREFIX + userId, ip, System.currentTimeMillis() + loginTimeout);
	}
}
