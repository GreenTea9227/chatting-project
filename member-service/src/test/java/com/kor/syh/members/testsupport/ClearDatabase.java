package com.kor.syh.members.testsupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@TestComponent
public class ClearDatabase {

	@Autowired
	private RedisTemplate redisTemplate;

	public void clear() {

		redisTemplate.execute((RedisConnection connection) -> {
			connection.execute("FLUSHDB");
			return null;
		});

	}
}
