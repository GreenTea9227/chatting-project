package com.kor.syh.chat;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public abstract class MongoTestContainers {

	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo");

	static {
		mongoDBContainer.start();
	}

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

}
