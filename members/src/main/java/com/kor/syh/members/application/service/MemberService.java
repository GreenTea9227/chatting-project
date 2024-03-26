package com.kor.syh.members.application.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kor.syh.members.adapter.out.exception.PasswordMisMatchException;
import com.kor.syh.members.application.port.in.member.FindMemberResponse;
import com.kor.syh.members.application.port.in.member.FindMemberUseCase;
import com.kor.syh.members.application.port.in.member.RegisterMemberCommand;
import com.kor.syh.members.application.port.in.member.RegisterMemberUseCase;
import com.kor.syh.members.application.port.out.member.FindMemberPort;
import com.kor.syh.members.application.port.out.member.RegisterMemberPort;
import com.kor.syh.members.domain.Member;
import com.kor.syh.members.domain.MemberStatus;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberService implements RegisterMemberUseCase, FindMemberUseCase {
	private final RegisterMemberPort registerMemberPort;
	private final FindMemberPort findMemberPort;
	private final PasswordEncoder passwordEncoder;

	@Override
	public void register(RegisterMemberCommand command) {
		String encodePassword = passwordEncoder.encode(command.getPassword());
		Member member = Member.builder()
							  .loginId(command.getLoginId())
							  .status(MemberStatus.USER)
							  .nickname(command.getNickname())
							  .password(encodePassword)
							  .username(command.getUsername())
							  .build();

		registerMemberPort.register(member);
	}

	@Override
	public FindMemberResponse find(String loginId, String password) {
		Member member = findMemberPort.findByLoginId(loginId);
		if (!passwordEncoder.matches(member.getPassword(), password)) {
			throw new PasswordMisMatchException("Password is not matched");
		}

		return new FindMemberResponse(
			member.getId(),
			member.getLoginId(),
			member.getUsername(),
			member.getNickname(),
			member.getStatus()
		);

	}
}
