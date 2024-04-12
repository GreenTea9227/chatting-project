package com.kor.syh.member.application.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kor.syh.member.adapter.in.web.RegisterMemberRequest;
import com.kor.syh.member.adapter.out.exception.DuplicateLoginIdException;
import com.kor.syh.member.adapter.out.exception.PasswordMisMatchException;
import com.kor.syh.member.application.port.in.member.FindMemberResponse;
import com.kor.syh.member.application.port.in.member.FindMemberUseCase;
import com.kor.syh.member.application.port.in.member.RegisterMemberUseCase;
import com.kor.syh.member.application.port.out.member.FindMemberPort;
import com.kor.syh.member.application.port.out.member.RegisterMemberPort;
import com.kor.syh.member.domain.Member;
import com.kor.syh.member.domain.MemberStatus;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberService implements RegisterMemberUseCase, FindMemberUseCase {
	private final RegisterMemberPort registerMemberPort;
	private final FindMemberPort findMemberPort;
	private final PasswordEncoder passwordEncoder;

	@Override
	public void register(RegisterMemberRequest request) {

		boolean isMember = findMemberPort.isExistsMember(request.getLoginId());
		if (isMember) {
			throw new DuplicateLoginIdException("중복 Id입니다.");
		}

		String encodePassword = passwordEncoder.encode(request.getPassword());
		Member member = Member.builder()
							  .loginId(request.getLoginId())
							  .status(MemberStatus.USER)
							  .nickname(request.getNickname())
							  .password(encodePassword)
							  .username(request.getUsername())
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
