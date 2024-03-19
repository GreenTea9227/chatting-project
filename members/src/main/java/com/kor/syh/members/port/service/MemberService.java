package com.kor.syh.members.port.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kor.syh.members.domain.Member;
import com.kor.syh.members.domain.MemberStatus;
import com.kor.syh.members.port.in.member.RegisterMemberCommand;
import com.kor.syh.members.port.in.member.RegisterMemberUseCase;
import com.kor.syh.members.port.out.member.RegisterMemberPort;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberService implements RegisterMemberUseCase {
	private final RegisterMemberPort registerMemberPort;

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
}
