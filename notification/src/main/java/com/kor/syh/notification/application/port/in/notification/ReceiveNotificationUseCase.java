package com.kor.syh.notification.application.port.in.notification;

import com.kor.syh.notification.adpater.in.channel.ReceiveMessage;

public interface ReceiveNotificationUseCase {
	void receive(String receiver, ReceiveMessage command);
}
