package com.kor.syh.members.application.in.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.stream.Stream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.ConstraintViolationException;

@AutoConfigureMockMvc
@SpringBootTest
class MemberControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;

	@AfterEach
	void cleaningData() {

	}

	@DisplayName("회원 가입 성공")
	@Test
	void success_register() throws Exception {
		// given
		String userId = "userId";
		String username = "user1";
		String password = "12341234";
		String nickname = "nickname1";

		RegisterMemberRequest request = new RegisterMemberRequest(userId, password, username, nickname);
		String requestStr = objectMapper.writeValueAsString(request);

		// when
		ResultActions perform = mvc.perform(post("/member/register")
			.contentType("application/json")
			.content(requestStr));

		// then
		perform.andExpect(status().is2xxSuccessful())
			   .andExpect(handler().handlerType(MemberController.class))
			   .andExpect(handler().methodName("register"))
			   .andExpect(jsonPath("$.status").value("success"))
			   .andExpect(jsonPath("$.data").isEmpty())
			   .andExpect(jsonPath("$.message").value("가입 성공"));
	}

	@DisplayName("회원 등록 값이 잘못된 경우 실패한다.")
	@ParameterizedTest(name = "{index} => {4}")
	@MethodSource("failRegisterMemberData")
	void fail_register_when_empty_value(String userId, String username, String password, String nickname,
		String description) throws
		Exception {
		// given
		RegisterMemberRequest request = new RegisterMemberRequest(userId, password, username, nickname);
		String requestStr = objectMapper.writeValueAsString(request);

		// when
		Assertions.assertThatThrownBy(() -> mvc.perform(post("/member/register")
					  .contentType("application/json")
					  .content(requestStr)))
				  .hasCauseInstanceOf(ConstraintViolationException.class);

	}

	static Stream<Arguments> failRegisterMemberData() {
		return Stream.of(
			Arguments.of("", "password", "username", "nickname", "userId 누락"),
			Arguments.of("userId", "", "username", "nickname", "password 누락"),
			Arguments.of("userId", "password", "", "nickname", "username 누락"),
			Arguments.of("userId", "password", "username", "", "nickname 누락")
		);
	}

}