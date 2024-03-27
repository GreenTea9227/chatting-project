package com.kor.syh.members.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringJpaTokenRepository extends JpaRepository<TokenEntity,String> {
	void deleteByMemberId(String userId);
}
