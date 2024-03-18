package com.kor.syh.application.port.in.notification;

import com.kor.syh.domain.Notify;

public interface ReceiveNotificationUseCase {
	void receive(Notify notify);
}
