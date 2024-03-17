package com.kor.syh.application.port.out.redis;

public interface MessageManagementPort {
	void subscribe(String memberId);
	void removeSubscribe(String memberId);
}
