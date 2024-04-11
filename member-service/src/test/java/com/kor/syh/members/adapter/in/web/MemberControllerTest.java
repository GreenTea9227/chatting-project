package com.kor.syh.members.adapter.in.web;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
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
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kor.syh.common.UnitTest;
import com.kor.syh.member.adapter.in.web.LoginMemberRequest;
import com.kor.syh.member.adapter.in.web.MemberController;
import com.kor.syh.member.adapter.in.web.RegisterMemberRequest;
import com.kor.syh.member.adapter.out.exception.MemberNotFoundException;
import com.kor.syh.member.application.port.in.auth.LoginMemberUseCase;
import com.kor.syh.member.application.port.in.auth.LogoutMemberUseCase;
import com.kor.syh.member.application.port.in.auth.TokenInfo;
import com.kor.syh.member.application.port.in.member.FindMemberUseCase;
import com.kor.syh.member.application.port.in.member.RegisterMemberUseCase;


@UnitTest
@AutoConfigureMockMvc
@SpringBootTest
class MemberControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private LoginMemberUseCase loginMemberUseCase;

	@MockBean
	private RegisterMemberUseCase registerMemberUseCase;

	@MockBean
	private FindMemberUseCase findMemberUseCase;

	@MockBean
	private LogoutMemberUseCase logoutMemberUseCase;

	@DisplayName("회원 가입")
	@Nested
	class RegisterTest {
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
			ResultActions perform = mvc.perform(post("/register")
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

		static Stream<Arguments> failRegisterMemberData() {
			return Stream.of(
				Arguments.of("", "password", "username", "nickname", "userId 누락"),
				Arguments.of("userId", "", "username", "nickname", "password 누락"),
				Arguments.of("userId", "password", "", "nickname", "username 누락"),
				Arguments.of("userId", "password", "username", "", "nickname 누락")
			);
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
			mvc.perform(post("/register")
				   .contentType("application/json")
				   .content(requestStr))
			   .andDo(print());

		}
	}

	@DisplayName("로그인")
	@Nested
	class LoginTest {
		@DisplayName("로그인 성공")
		@Test
		void success_login() throws Exception {
			// given
			String password = "1111111";
			String loginId = "loginId";
			LoginMemberRequest request = new LoginMemberRequest(loginId, password);
			String requestStr = objectMapper.writeValueAsString(request);

			String accessToken = "accessToken-value";
			String refreshToken = "refresh-token-value";
			TokenInfo tokenInfo = new TokenInfo(accessToken,refreshToken);
			when(loginMemberUseCase.login(eq(loginId), eq(password), any())).thenReturn(tokenInfo);

			// when
			ResultActions perform = mvc.perform(post("/login")
				.contentType("application/json")
				.content(requestStr));

			// then
			perform.andExpect(status().is2xxSuccessful())
				   .andExpect(handler().handlerType(MemberController.class))
				   .andExpect(handler().methodName("login"))
				   .andExpect(jsonPath("$.status").value("success"))
				   .andExpect(jsonPath("$.data.accessToken").value(accessToken))
				   .andExpect(jsonPath("$.data.refreshToken").value(refreshToken))
				   .andExpect(jsonPath("$.message").value("로그인 성공"));
		}

		@DisplayName("로그인 실패")
		@Test
		void fail_login() throws Exception {
			// given
			String password = "1111111";
			String loginId = "loginId";
			String ip = "0.0.0.0";
			LoginMemberRequest request = new LoginMemberRequest(loginId, password);
			String requestStr = objectMapper.writeValueAsString(request);
			when(loginMemberUseCase.login(eq(request.getLoginId()), eq(request.getPassword()), any())).thenThrow(
				MemberNotFoundException.class);

			// when, then

			ResultActions perform = mvc.perform(post("/login")
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

	@Nested
	public class PageAccessTest {

		@WithAnonymousUser
		@DisplayName("로그인 하지 않은 경우 권한이 필요한 페이지 접근에 실패한다.")
		@Test
		void unauthorized_access_fails() throws Exception {
			// when, then
			mvc.perform(get("/randomPage"))
			   .andExpect(status().isUnauthorized());
		}

		@WithMockUser
		@DisplayName("404 page test")
		@Test
		void not_found_page_returns_404() throws Exception {
			// when, then
			mvc.perform(get("/nonExistentPage"))
			   .andExpect(status().isNotFound());
		}

		@WithMockUser
		@DisplayName("인증된 유저는 로그아웃을 할 수 있다")
		@Test
		void authenticated_user_can_logout_successfully() throws Exception {
			// given
			mvc.perform(get("/logout"))
			   .andExpect(status().isOk())
			   .andExpect(jsonPath("$.status").value("success"))
			   .andExpect(jsonPath("$.data").isEmpty())
			   .andExpect(jsonPath("$.message").value("로그아웃 성공"));
		}

	}

}