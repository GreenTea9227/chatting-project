package com.kor.syh.member.application.port.out.member;

import com.kor.syh.member.domain.Member;

public interface FindMemberPort {
	Member findByLoginId(String loginId);
	Member findByMemberId(String memberId);
	boolean isExistsMember(String loginId);
}
