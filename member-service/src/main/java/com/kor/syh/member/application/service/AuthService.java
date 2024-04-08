package com.kor.syh.member.application.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kor.syh.common.jwt.JwtCreateRequestDto;
import com.kor.syh.common.jwt.JwtUtils;
import com.kor.syh.member.adapter.out.exception.PasswordMisMatchException;
import com.kor.syh.member.application.port.in.auth.LoginMemberUseCase;
import com.kor.syh.member.application.port.out.member.FindMemberPort;
import com.kor.syh.member.domain.Member;
import com.kor.syh.member.application.port.in.auth.LogoutMemberUseCase;
import com.kor.syh.member.application.port.out.member.LoginStatusPort;
import com.kor.syh.member.application.port.out.member.LogoutStatusPort;
import com.kor.syh.member.application.port.out.member.TokenStoragePort;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthService implements LoginMemberUseCase, LogoutMemberUseCase {

	private final FindMemberPort findMemberPort;
	private final JwtUtils jwtUtils;
	private final PasswordEncoder passwordEncoder;
	private final TokenStoragePort tokenStoragePort;
	private final LoginStatusPort loginStatusPort;
	private final LogoutStatusPort logoutStatusPort;


	@Override
	public String login(String loginId, String password, String clientIp) {
		Member member = findMemberPort.findByLoginId(loginId);

		if (!passwordEncoder.matches(password, member.getPassword())) {
			throw new PasswordMisMatchException("Password is not matched");
		}


		JwtCreateRequestDto requestDto = JwtCreateRequestDto.builder()
															.username(member.getUsername())
															.id(member.getId())
															.build();
		String token = jwtUtils.generateJwtToken(requestDto);

		//TODO 사용자 기기 정보 식별
		tokenStoragePort.saveToken(member.getId(),token);
		loginStatusPort.login(member.getId(), clientIp);

		return token;

	}

	@Override
	public void logout(String userId, String clientIp) {

		tokenStoragePort.deleteToken(userId);
		logoutStatusPort.logout(userId, clientIp);

	}
}
