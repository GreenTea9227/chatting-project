package com.kor.syh.member.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class MemberConfig {
	private final RedisProperties redisProperties;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(
			redisProperties.getHost(),
			redisProperties.getPort());
		redisStandaloneConfiguration.setPassword(redisProperties.getPassword());
		return new LettuceConnectionFactory(redisStandaloneConfiguration);
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		final RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

		// Key Serializer 설정
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		// Value Serializer 설정
		redisTemplate.setValueSerializer(new StringRedisSerializer());
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
		redisTemplate.setConnectionFactory(redisConnectionFactory);

		return redisTemplate;
	}
}
