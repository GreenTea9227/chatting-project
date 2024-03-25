package com.kor.syh.members.application.service;

import org.springframework.stereotype.Service;

import com.kor.syh.common.jwt.TokenProvider;
import com.kor.syh.members.domain.Member;
import com.kor.syh.members.application.port.in.auth.LoginMemberUseCase;
import com.kor.syh.members.application.port.out.member.FindMemberPort;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthService implements LoginMemberUseCase {

	private final FindMemberPort findMemberPort;
	private final TokenProvider tokenProvider;
	@Override
	public String login(String loginId, String password) {
		Member member = findMemberPort.find(loginId, password);
		return member.getId();
	}
}
