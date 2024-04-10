package com.kor.syh.member.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "spring.data.redis")
public class RedisProperties {
	private String host;

	private int port;

	private int database;

	private String password;
}
