package com.kor.syh.member.application.port.in.auth;

public interface LoginMemberUseCase {
	String login(String loginId, String password, String clientIp);
}