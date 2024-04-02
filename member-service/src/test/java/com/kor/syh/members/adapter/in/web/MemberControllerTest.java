package com.kor.syh.members.adapter.in.web;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kor.syh.member.adapter.in.web.LoginMemberRequest;
import com.kor.syh.member.adapter.in.web.MemberController;
import com.kor.syh.member.adapter.in.web.RegisterMemberRequest;
import com.kor.syh.member.adapter.out.exception.MemberNotFoundException;
import com.kor.syh.member.application.port.in.auth.LoginMemberUseCase;

import jakarta.validation.ConstraintViolationException;

@AutoConfigureMockMvc
@SpringBootTest
class MemberControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private LoginMemberUseCase loginMemberUseCase;

	@AfterEach
	void cleaningData() {

	}

	@DisplayName("회원 가입")
	@Nested
	class RegisterTest {
		static Stream<Arguments> failRegisterMemberData() {
			return Stream.of(
				Arguments.of("", "password", "username", "nickname", "userId 누락"),
				Arguments.of("userId", "", "username", "nickname", "password 누락"),
				Arguments.of("userId", "password", "", "nickname", "username 누락"),
				Arguments.of("userId", "password", "username", "", "nickname 누락")
			);
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
			assertThatThrownBy(() -> mvc.perform(post("/member/register")
				.contentType("application/json")
				.content(requestStr)))
				.hasCauseInstanceOf(ConstraintViolationException.class);

		}
	}

	@DisplayName("로그인")
	@Nested
	class LoginTest {
		@DisplayName("로그인 성공")
		@Test
		void success_login() throws Exception {
			// given
			String password = "1111";
			String loginId = "loginId";
			LoginMemberRequest request = new LoginMemberRequest(loginId, password);
			String requestStr = objectMapper.writeValueAsString(request);

			String jwtToken = "jwt-token-value";
			when(loginMemberUseCase.login(eq(loginId), eq(password),any())).thenReturn(jwtToken);

			// when
			ResultActions perform = mvc.perform(post("/member/login")
				.contentType("application/json")
				.content(requestStr));

			// then
			perform.andExpect(status().is2xxSuccessful())
				   .andExpect(handler().handlerType(MemberController.class))
				   .andExpect(handler().methodName("login"))
				   .andExpect(jsonPath("$.status").value("success"))
				   .andExpect(jsonPath("$.data.accessToken").value(jwtToken))
				   .andExpect(jsonPath("$.message").isEmpty());
		}

		@DisplayName("로그인 실패")
		@Test
		void fail_login() throws Exception {
			// given
			String password = "1111";
			String loginId = "loginId";
			String ip = "0.0.0.0";
			LoginMemberRequest request = new LoginMemberRequest(loginId, password);
			String requestStr = objectMapper.writeValueAsString(request);
			when(loginMemberUseCase.login(eq(request.getLoginId()), eq(request.getPassword()),any())).thenThrow(
				MemberNotFoundException.class);

			// when, then

			ResultActions perform = mvc.perform(post("/member/login")
				.contentType("application/json")
				.content(requestStr));

			// then
			perform.andExpect(status().is4xxClientError())
				   .andExpect(handler().handlerType(MemberController.class))
				   .andExpect(handler().methodName("login"))
				   .andExpect(jsonPath("$.status").value("fail"))
				   .andExpect(jsonPath("$.data").isEmpty())
				   .andExpect(jsonPath("$.message").value("존재하지 않는 회원입니다."));

		}
	}

}