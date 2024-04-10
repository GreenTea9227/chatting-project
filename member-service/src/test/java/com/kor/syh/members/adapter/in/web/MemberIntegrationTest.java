package com.kor.syh.members.adapter.in.web;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import com.kor.syh.common.IntegrationTest;
import com.kor.syh.member.adapter.in.web.RegisterMemberRequest;
import com.kor.syh.member.adapter.out.persistence.MemberJpaEntity;
import com.kor.syh.member.domain.MemberStatus;
import com.kor.syh.members.testsupport.IntegrationTestEnvironment;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin;

@DisplayName("회원 통합 테스트")
@IntegrationTest
public class MemberIntegrationTest extends IntegrationTestEnvironment {

	private final FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
															 .defaultNotNull(true)
															 .objectIntrospector(
																 FieldReflectionArbitraryIntrospector.INSTANCE)
															 .plugin(new JakartaValidationPlugin())
															 .build();

	@DisplayName("회원 가입")
	@Test
	void member_register() throws Exception {
		// given
		RegisterMemberRequest request = fixtureMonkey.giveMeBuilder(RegisterMemberRequest.class)
													 .sample();

		String requestString = objectMapper.writeValueAsString(request);

		// when
		ResultActions perform = mvc.perform(post("/register")
			.content(requestString)
			.contentType(MediaType.APPLICATION_JSON_VALUE));

		// then
		MemberJpaEntity memberJpaEntity = springJpaMemberRepository.findByLoginId(request.getLoginId()).orElseThrow();

		perform.andExpect(status().isCreated());
		assertThat(memberJpaEntity.getId()).isNotBlank();
		assertThat(memberJpaEntity.getNickname()).isEqualTo(request.getNickname());
		assertThat(passwordEncoder.matches(request.getPassword(), memberJpaEntity.getPassword())).isTrue();
		assertThat(memberJpaEntity.getUsername()).isEqualTo(request.getUsername());
	}

	@DisplayName("회원 로그인")
	@Test
	void member_login() throws Exception {
		// given
		RegisterMemberRequest request = fixtureMonkey.giveMeOne(RegisterMemberRequest.class);
		springJpaMemberRepository.save(new MemberJpaEntity(
			UUID.randomUUID().toString(),
			request.getLoginId(),
			passwordEncoder.encode(request.getPassword()),
			request.getUsername(),
			request.getNickname(),
			MemberStatus.USER
		));

		String requestString = objectMapper.writeValueAsString(request);

		//when
		ResultActions perform = mvc.perform(post("/login")
			.content(requestString)
			.contentType(MediaType.APPLICATION_JSON_VALUE));

		// then
		perform.andExpect(status().isOk())
			   .andExpect(jsonPath("$.data.accessToken").isString())
			   .andExpect(jsonPath("$.message").value("로그인 성공"))
			   .andExpect(jsonPath("$.status").value("success"));

	}

}
