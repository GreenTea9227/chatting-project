package com.kor.syh.member.application.port.in.member;

public interface FindMemberUseCase {
	FindMemberResponse find(String loginId, String password);
}
