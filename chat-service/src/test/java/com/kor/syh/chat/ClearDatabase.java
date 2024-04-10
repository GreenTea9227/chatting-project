package com.kor.syh.chat;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@TestComponent
public class ClearDatabase {

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private MongoTemplate mongoTemplate;

	public void clear() {

		redisTemplate.execute((RedisConnection connection) -> {
			connection.execute("FLUSHDB");
			return null;
		});

		Set<String> collectionNames = mongoTemplate.getCollectionNames();
		for (String collectionName : collectionNames) {
			mongoTemplate.dropCollection(collectionName);
		}

	}
}
