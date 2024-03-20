package com.kor.syh.members.port.out.member;

import com.kor.syh.members.domain.Member;

public interface FindMemberPort {
	Member find(String loginId, String password);
}
