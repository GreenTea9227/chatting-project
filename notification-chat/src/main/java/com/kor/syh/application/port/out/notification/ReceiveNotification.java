package com.kor.syh.application.port.out.notification;

import com.kor.syh.domain.Notify;

public interface ReceiveNotification {
	void receive(Notify notify);
}
