package com.kor.syh;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Getter
@Configuration
@ConfigurationProperties(prefix = "redis")
public class RedisProperties {
	private String host;

	private int port;

	private int database;
}
