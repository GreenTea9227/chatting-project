package com.kor.syh.chat;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
public class RedisTestContainers {

	@Container
	private static final GenericContainer<?> redis = new GenericContainer<>(DockerImageName.parse("redis"))
		.withExposedPorts(6379);

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry registry) {
		registry.add("redis.host", () -> redis.getHost());
		registry.add("redis.port", () -> redis.getFirstMappedPort());
		registry.add("redis.password", () -> "1111");
		registry.add("redis.database", () -> 0);
	}

}
