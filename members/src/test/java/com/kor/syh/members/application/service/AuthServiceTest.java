package com.kor.syh.members.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.kor.syh.common.jwt.JwtCreateRequestDto;
import com.kor.syh.common.jwt.TokenProvider;
import com.kor.syh.members.application.port.out.member.FindMemberPort;
import com.kor.syh.members.domain.Member;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

	@InjectMocks
	private AuthService authService;
	@Mock
	private  FindMemberPort findMemberPort;
	@Mock
	private  TokenProvider tokenProvider;

	@DisplayName("로그인 인증 성공")
	@Test
	void success_login() {
		// given
		String loginId = "loginId";
		String password = "1111";
		String username = "user1";
		Member member = Member.builder()
							  .loginId(loginId)
							  .password(password)
							  .username(username)
							  .build();
		when(findMemberPort.find(loginId,password)).thenReturn(member);
		String jwtToken = "jwt-token-value";
		when(tokenProvider.generateJwtToken(any(JwtCreateRequestDto.class))).thenReturn(jwtToken);
		// when

		String responseJwt = authService.login(loginId, password);

		// then
		assertThat(responseJwt).isEqualTo(jwtToken);
	}

}