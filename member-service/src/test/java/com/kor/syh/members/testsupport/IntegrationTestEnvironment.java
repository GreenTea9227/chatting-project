package com.kor.syh.members.testsupport;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kor.syh.member.adapter.out.persistence.SpringJpaMemberRepository;
import com.netflix.discovery.converters.Auto;

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
	protected ObjectMapper objectMapper;

	@Autowired
	protected SpringJpaMemberRepository springJpaMemberRepository;

	@Autowired
	protected PasswordEncoder passwordEncoder;

}
