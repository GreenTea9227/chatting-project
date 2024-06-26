package com.kor.syh.members.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.kor.syh.common.UnitTest;
import com.kor.syh.common.jwt.JwtCreateRequestDto;
import com.kor.syh.common.jwt.JwtUtils;
import com.kor.syh.member.adapter.out.exception.PasswordMisMatchException;
import com.kor.syh.member.application.port.in.auth.TokenInfo;
import com.kor.syh.member.application.port.out.member.FindMemberPort;
import com.kor.syh.member.application.port.out.member.LoginStatusPort;
import com.kor.syh.member.application.port.out.member.TokenStoragePort;
import com.kor.syh.member.application.service.AuthService;
import com.kor.syh.member.domain.Member;

@UnitTest
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

	@InjectMocks
	private AuthService authService;
	@Mock
	private FindMemberPort findMemberPort;
	@Mock
	private JwtUtils jwtUtils;
	@Mock
	private LoginStatusPort loginStatusPort;
	@Mock
	private PasswordEncoder passwordEncoder;
	@Mock
	private TokenStoragePort tokenStoragePort;

	@DisplayName("로그인 인증 성공")
	@Test
	void success_login() {
		// given
		String loginId = "loginId";
		String password = "1111";
		String username = "user1";
		String ip = "0.0.0.0";
		Member member = Member.builder()
							  .loginId(loginId)
							  .password(password)
							  .username(username)
							  .build();
		when(findMemberPort.findByLoginId(loginId)).thenReturn(member);
		when(passwordEncoder.matches(password, member.getPassword())).thenReturn(true);
		doNothing().when(loginStatusPort).login(any(), any());
		String accessToken = "accessToken";
		String refreshToken = "refreshToken";
		doNothing().when(tokenStoragePort).saveToken(member.getId(),refreshToken);
		when(jwtUtils.generateJwtToken(any(JwtCreateRequestDto.class))).thenReturn(accessToken);
		when(jwtUtils.generateRefreshToken(any(JwtCreateRequestDto.class))).thenReturn(refreshToken);
		// when

		TokenInfo tokenInfo = authService.login(loginId, password, ip);

		// then
		assertThat(tokenInfo.getAccessToken()).isEqualTo(accessToken);
		assertThat(tokenInfo.getRefreshToken()).isEqualTo(refreshToken);
	}

	@DisplayName("로그인 실패")
	@Test
	void fail_login() {
		// given
		String loginId = "loginId";
		String password = "1111";
		String username = "user1";
		String ip = "0.0.0.0";
		Member member = Member.builder()
							  .loginId(loginId)
							  .password(password)
							  .username(username)
							  .build();
		when(findMemberPort.findByLoginId(loginId)).thenReturn(member);
		when(passwordEncoder.matches(password, member.getPassword())).thenReturn(false);

		// when

		assertThatThrownBy(() -> authService.login(loginId, password, ip))
			.isInstanceOf(PasswordMisMatchException.class);

	}

}