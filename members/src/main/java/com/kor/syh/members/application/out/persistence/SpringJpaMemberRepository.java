package com.kor.syh.members.application.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringJpaMemberRepository extends JpaRepository<MemberJpaEntity,String> {
}
