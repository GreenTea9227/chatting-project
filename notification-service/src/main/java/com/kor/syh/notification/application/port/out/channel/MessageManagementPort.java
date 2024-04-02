package com.kor.syh.notification.application.port.out.channel;

public interface MessageManagementPort {
	void subscribe(String memberId);

	void removeSubscribe(String memberId);
}
