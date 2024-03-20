package com.kor.syh.members.port.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kor.syh.members.domain.Member;
import com.kor.syh.members.domain.MemberStatus;
import com.kor.syh.members.port.in.member.FindMemberResponse;
import com.kor.syh.members.port.in.member.FindMemberUseCase;
import com.kor.syh.members.port.in.member.RegisterMemberCommand;
import com.kor.syh.members.port.in.member.RegisterMemberUseCase;
import com.kor.syh.members.port.out.member.FindMemberPort;
import com.kor.syh.members.port.out.member.RegisterMemberPort;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberService implements RegisterMemberUseCase, FindMemberUseCase {
	private final RegisterMemberPort registerMemberPort;
	private final FindMemberPort findMemberPort;

	@Override
	public void register(RegisterMemberCommand command) {

		Member member = Member.builder()
							  .loginId(command.getLoginId())
							  .status(MemberStatus.USER)
							  .nickname(command.getNickname())
							  .password(command.getPassword())
							  .username(command.getUsername())
							  .build();

		registerMemberPort.register(member);
	}

	@Override
	public FindMemberResponse find(String loginId, String password) {
		Member member = findMemberPort.find(loginId, password);

		return new FindMemberResponse(
			member.getId(),
			member.getLoginId(),
			member.getNickname(),
			member.getUsername(),
			member.getStatus()
		);

	}
}
