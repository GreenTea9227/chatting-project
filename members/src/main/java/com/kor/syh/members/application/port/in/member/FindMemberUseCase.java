package com.kor.syh.members.application.port.in.member;

public interface FindMemberUseCase {
	FindMemberResponse find(String loginId, String password);
}
