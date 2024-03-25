package com.kor.syh.members.port.service;

import org.springframework.stereotype.Service;

import com.kor.syh.members.domain.Member;
import com.kor.syh.members.port.in.auth.LoginMemberUseCase;
import com.kor.syh.members.port.out.member.FindMemberPort;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthService implements LoginMemberUseCase {

	private final FindMemberPort findMemberPort;
	@Override
	public String login(String loginId, String password) {
		Member member = findMemberPort.find(loginId, password);
		return member.getId();
	}
}
