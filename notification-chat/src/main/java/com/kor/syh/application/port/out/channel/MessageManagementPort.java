package com.kor.syh.application.port.out.channel;

public interface MessageManagementPort {
	void subscribe(String memberId);

	void removeSubscribe(String memberId);
}
