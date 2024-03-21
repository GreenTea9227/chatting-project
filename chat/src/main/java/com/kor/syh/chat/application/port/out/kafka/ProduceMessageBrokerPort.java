package com.kor.syh.chat.application.port.out.kafka;

import com.kor.syh.chat.domain.Chat;

public interface ProduceMessageBrokerPort {
	void produce(String topic, Chat message);
}
