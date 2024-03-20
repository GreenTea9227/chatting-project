package com.kor.syh.members.application.out.persistence;

import org.springframework.stereotype.Component;

import com.kor.syh.members.domain.Member;

@Component
public class MemberMapper {
	public MemberJpaEntity toEntity(Member member) {
		return new MemberJpaEntity(
			member.getId(),
			member.getLoginId(),
			member.getPassword(),
			member.getUsername(),
			member.getNickname(),
			member.getStatus()
		);
	}

	public Member toDomain(MemberJpaEntity memberJpaEntity) {
		return new Member(
			memberJpaEntity.getId(),
			memberJpaEntity.getLoginId(),
			memberJpaEntity.getPassword(),
			memberJpaEntity.getUsername(),
			memberJpaEntity.getNickname(),
			memberJpaEntity.getStatus(),
			memberJpaEntity.getCreateDate(),
			memberJpaEntity.getUpdateDate()
		);
	}
}
