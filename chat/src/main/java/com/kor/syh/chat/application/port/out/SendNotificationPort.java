package com.kor.syh.chat.application.port.out;

public interface SendNotificationPort {
	void sendNotification(String fromId, String content);
}
