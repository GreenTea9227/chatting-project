package com.kor.syh.application.port.in;

public interface SendNotificationUseCase {
	void send(String sender, String receiver, String content);
}
