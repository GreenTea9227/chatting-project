package com.kor.syh.members.application.out.persistence;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.kor.syh.members.domain.Member;
import com.kor.syh.members.port.out.member.RegisterMemberPort;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class MemberRepository implements RegisterMemberPort {

	private final SpringJpaMemberRepository memberRepository;
	private final MemberMapper memberMapper;

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
}
