package com.kor.syh.application.port.in.notification;

import com.kor.syh.adpater.in.channel.ReceiveMessage;

public interface ReceiveNotificationUseCase {
	void receive(String receiver, ReceiveMessage command);
}
