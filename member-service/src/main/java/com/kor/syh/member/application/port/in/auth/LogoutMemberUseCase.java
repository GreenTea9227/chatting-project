package com.kor.syh.member.application.port.in.auth;

public interface LogoutMemberUseCase {
	void logout(String userId, String clientIp);
}
