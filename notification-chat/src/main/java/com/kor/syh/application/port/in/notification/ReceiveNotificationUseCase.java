package com.kor.syh.application.port.in.notification;

import com.kor.syh.adpater.in.redis.ReceiveMessage;

public interface ReceiveNotificationUseCase {
	void receive(String receiver, ReceiveMessage command);
}
