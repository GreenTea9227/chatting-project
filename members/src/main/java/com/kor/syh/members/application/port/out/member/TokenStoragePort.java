package com.kor.syh.members.application.port.out.member;

public interface TokenStoragePort {
	void saveToken(String userId, String token);

	void deleteToken(String userId);
}
