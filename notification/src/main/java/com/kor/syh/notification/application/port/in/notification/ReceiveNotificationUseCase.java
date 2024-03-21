package com.kor.syh.notification.application.port.in.notification;

import com.kor.syh.common.PublishNotificationDto;

public interface ReceiveNotificationUseCase {
	void receive(String receiver, PublishNotificationDto command);
}
