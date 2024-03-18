package com.kor.syh.application.port.out.channel;

public interface MessagePublishPort {

	Long publish(String receiver, SendMessage message);

}
