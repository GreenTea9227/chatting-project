package com.kor.syh.application.port.out.notification;

import com.kor.syh.application.port.in.notification.SendMessageCommand;

public interface SendNotificationUseCase {
	Long send(SendMessageCommand sendMessageCommand);
}
