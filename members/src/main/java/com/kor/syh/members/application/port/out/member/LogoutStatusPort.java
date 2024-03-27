package com.kor.syh.members.application.port.out.member;

public interface LogoutStatusPort {
	void logout(String userId, String clientIp);
}
