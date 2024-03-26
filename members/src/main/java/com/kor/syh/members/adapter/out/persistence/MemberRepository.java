package com.kor.syh.members.adapter.out.persistence;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.kor.syh.members.adapter.out.exception.MemberNotFoundException;
import com.kor.syh.members.domain.Member;
import com.kor.syh.members.application.port.out.member.FindMemberPort;
import com.kor.syh.members.application.port.out.member.RegisterMemberPort;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class MemberRepository implements RegisterMemberPort, FindMemberPort {

	private final SpringJpaMemberRepository memberRepository;
	private final MemberMapper mapper;

	@Override
	public void register(Member member) {

		//TODO password 암호화 필요
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
}
