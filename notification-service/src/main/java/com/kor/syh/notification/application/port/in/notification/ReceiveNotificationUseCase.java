package com.kor.syh.notification.application.port.in.notification;

import com.kor.syh.common.RedisPubSubNotification;

public interface ReceiveNotificationUseCase {
	void receive(String receiver, RedisPubSubNotification command);
}
