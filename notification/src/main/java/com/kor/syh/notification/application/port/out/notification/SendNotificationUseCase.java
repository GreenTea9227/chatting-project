package com.kor.syh.notification.application.port.out.notification;

import com.kor.syh.notification.application.port.in.notification.SendMessageCommand;

public interface SendNotificationUseCase {
	Long send(SendMessageCommand sendMessageCommand);
}
