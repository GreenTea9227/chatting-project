package com.kor.syh.loggingservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ConfigurationProperties(prefix = "kafka.logging")
public class KafkaProperties {
	public final String SERVER;
	public final String GROUP_ID;
	public final String TOPIC;
}
