package com.kor.syh.members.application.port.out.member;

import com.kor.syh.members.domain.Member;

public interface FindMemberPort {
	Member findByLoginId(String loginId);
}
