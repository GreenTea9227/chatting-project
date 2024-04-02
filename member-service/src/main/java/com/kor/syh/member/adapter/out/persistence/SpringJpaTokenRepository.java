package com.kor.syh.member.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringJpaTokenRepository extends JpaRepository<TokenEntity,String> {
	void deleteByMemberId(String userId);
}
