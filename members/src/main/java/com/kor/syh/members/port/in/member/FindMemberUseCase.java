package com.kor.syh.members.port.in.member;

public interface FindMemberUseCase {
	FindMemberResponse find(String loginId, String password);
}
