package com.kor.syh.members.port.out.member;

import java.util.Optional;

import com.kor.syh.members.domain.Member;

public interface FindMemberPort {
	Member find(String loginId,String password);
}
