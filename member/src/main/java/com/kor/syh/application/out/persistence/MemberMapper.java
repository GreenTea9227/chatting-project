package com.kor.syh.application.out.persistence;

import org.springframework.stereotype.Component;

import com.kor.syh.domain.Member;
import com.kor.syh.domain.MemberStatus;

@Component
public class MemberMapper {
	public MemberJpaEntity toEntity(Member member) {
		return new MemberJpaEntity(
			member.getId(),
			member.getLoginId(),
			member.getPassword(),
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
