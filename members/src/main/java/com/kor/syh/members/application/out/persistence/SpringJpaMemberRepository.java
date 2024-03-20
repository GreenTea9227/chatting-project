package com.kor.syh.members.application.out.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringJpaMemberRepository extends JpaRepository<MemberJpaEntity, String> {
	Optional<MemberJpaEntity> findByLoginIdAndPassword(String loginId, String password);
}
