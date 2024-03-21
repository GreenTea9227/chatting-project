package com.kor.syh.chat.application.port.in.kafka;

public interface ConsumeMessageBrokerPort {
	void consume(String message);
}
