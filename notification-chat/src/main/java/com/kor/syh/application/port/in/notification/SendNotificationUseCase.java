package com.kor.syh.application.port.in.notification;

public interface SendNotificationUseCase {
	Long send(SendMessageCommand sendMessageCommand);
}
