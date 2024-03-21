package com.kor.syh.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ConfigurationProperties(prefix = "custom.kafka")
public class KafkaConstant {

	public final String TOPIC_ID;
	public final String GROUP_ID;
	public final String SERVER;

}
