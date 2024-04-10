package com.kor.syh.members.testsupport;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

import com.redis.testcontainers.RedisContainer;

public abstract class IntegrationTestContainers {

	private static final RedisContainer redisContainer = new RedisContainer(DockerImageName.parse("redis"))
		.withExposedPorts(6379)
		.withReuse(true);

	static {
		redisContainer.start();
	}

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry registry) {
		setRedisProperties(registry);
	}

	private static void setRedisProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.data.redis.host", () -> redisContainer.getHost());
		registry.add("spring.data.redis.port", () -> redisContainer.getFirstMappedPort());
		registry.add("spring.data.redis.password", () -> "1111");
		registry.add("spring.data.redis.database", () -> 0);
	}

}
