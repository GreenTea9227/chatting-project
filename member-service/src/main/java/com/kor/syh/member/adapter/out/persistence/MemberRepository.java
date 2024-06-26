package com.kor.syh.member.adapter.out.persistence;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.kor.syh.member.adapter.out.exception.MemberNotFoundException;
import com.kor.syh.member.application.port.out.member.FindMemberPort;
import com.kor.syh.member.application.port.out.member.RegisterMemberPort;
import com.kor.syh.member.domain.Member;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class MemberRepository implements RegisterMemberPort, FindMemberPort {

	private final SpringJpaMemberRepository memberRepository;
	private final MemberMapper mapper;

	@Override
	public void register(Member member) {

		MemberJpaEntity jpaEntity = new MemberJpaEntity(
			UUID.randomUUID().toString(),
			member.getLoginId(),
			member.getPassword(),
			member.getUsername(),
			member.getNickname(),
			member.getStatus());
		memberRepository.save(jpaEntity);
	}

	@Override
	public Member findByLoginId(String loginId) {
		MemberJpaEntity jpaEntity = memberRepository.findByLoginId(loginId)
													.orElseThrow(MemberNotFoundException::new);

		return mapper.toDomain(jpaEntity);
	}

	@Override
	public Member findByMemberId(String memberId) {
		MemberJpaEntity jpaEntity = memberRepository.findById(memberId)
															   .orElseThrow(MemberNotFoundException::new);
		return mapper.toDomain(jpaEntity);
	}

	@Override
	public boolean isExistsMember(String loginId) {
		return memberRepository.existsByLoginId(loginId);
	}
}
