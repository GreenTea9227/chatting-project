package com.kor.syh.member.adapter.out.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringJpaMemberRepository extends JpaRepository<MemberJpaEntity, String> {
	Optional<MemberJpaEntity> findByLoginId(String loginId);
}
