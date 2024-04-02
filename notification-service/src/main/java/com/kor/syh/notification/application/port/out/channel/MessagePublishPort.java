package com.kor.syh.notification.application.port.out.channel;

public interface MessagePublishPort {

	Long publish(String receiver, SendMessage message);

}
