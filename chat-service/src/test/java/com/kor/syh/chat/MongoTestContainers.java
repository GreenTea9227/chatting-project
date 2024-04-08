package com.kor.syh.chat;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
public class MongoTestContainers {

	@Container
	private static final GenericContainer<?> redis = new GenericContainer<>(DockerImageName.parse("redis"))
		.withExposedPorts(6379);

	@Container
	private static final GenericContainer<?> mongoDBContainer = new GenericContainer(DockerImageName.parse("mongo"))
		.withEnv("MONGO_INITDB_ROOT_USERNAME", "john")
		.withEnv("MONGO_INITDB_ROOT_PASSWORD", "1111")
		.withEnv("MONGO_INITDB_DATABASE", "chat")
		.withExposedPorts(27017);

	static {
		mongoDBContainer.start();
	}

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry registry) {
		String mongoUri = String.format("mongodb://%s:%s@%s:%d/%s?authSource=admin",
			"john", // MongoDB 사용자 이름
			"1111", // MongoDB 비밀번호
			mongoDBContainer.getHost(), // 동적으로 할당된 MongoDB 컨테이너의 호스트
			mongoDBContainer.getFirstMappedPort(), // 동적으로 할당된 MongoDB 컨테이너의 포트
			"chat" // 사용할 MongoDB 데이터베이스 이름
		);

		registry.add("spring.data.mongodb.uri", () -> mongoUri);
	}

}
