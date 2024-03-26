package com.kor.syh.members.application.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kor.syh.common.jwt.JwtCreateRequestDto;
import com.kor.syh.common.jwt.TokenProvider;
import com.kor.syh.members.adapter.out.exception.PasswordMisMatchException;
import com.kor.syh.members.domain.Member;
import com.kor.syh.members.application.port.in.auth.LoginMemberUseCase;
import com.kor.syh.members.application.port.out.member.FindMemberPort;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthService implements LoginMemberUseCase {

	private final FindMemberPort findMemberPort;
	private final TokenProvider tokenProvider;
	private final PasswordEncoder passwordEncoder;
	@Override
	public String login(String loginId, String password) {
		Member member = findMemberPort.findByLoginId(loginId);

		if (!passwordEncoder.matches( password,member.getPassword())) {
			throw new PasswordMisMatchException("Password is not matched");
		}

		JwtCreateRequestDto requestDto = JwtCreateRequestDto.builder()
													   .username(member.getUsername())
													   .id(member.getId())
													   .build();
		return tokenProvider.generateJwtToken(requestDto);

	}
}
