package com.kor.syh.members.application.port.in.auth;

public interface LogoutMemberUseCase {
	void logout(String userId, String clientIp);
}
