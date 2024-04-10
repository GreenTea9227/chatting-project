package com.kor.syh.testsupport;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(ClearExtension.class)
@Import(ClearDatabase.class)
@AutoConfigureMockMvc
@SpringBootTest
public class IntegrationTestEnvironment extends IntegrationTestContainers {

	@Autowired
	protected MockMvc mvc;

	@Autowired
	protected RedisTemplate<String, String> redisTemplate;

	@Autowired
	protected MongoTemplate mongoTemplate;
}
