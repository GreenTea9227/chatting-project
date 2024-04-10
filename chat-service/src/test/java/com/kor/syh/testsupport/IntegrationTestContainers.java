package com.kor.syh.testsupport;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

public abstract class IntegrationTestContainers {

	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo")
		.withReuse(true);

	static final GenericContainer<?> redisContainer = new GenericContainer<>(DockerImageName.parse("redis"))
		.withExposedPorts(6379)
		.withReuse(true);

	static {
		mongoDBContainer.start();
		redisContainer.start();
	}

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry registry) {
		setMongoProperties(registry);
		setRedisProperties(registry);
	}

	private static void setMongoProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	private static void setRedisProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.data.redis.host", () -> redisContainer.getHost());
		registry.add("spring.data.redis.port", () -> redisContainer.getFirstMappedPort());
		registry.add("spring.data.redis.password", () -> "1111");
		registry.add("spring.data.redis.database", () -> 0);
	}

}
