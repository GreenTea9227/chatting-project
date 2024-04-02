package com.kor.syh.member.application.port.out.member;

public interface LogoutStatusPort {
	void logout(String userId, String clientIp);
}
