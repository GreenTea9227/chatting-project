package com.kor.syh.chat.application.port.out.kafka;

import com.kor.syh.chat.domain.Message;

public interface ProduceMessageBrokerPort {
	void produce(Message message);
}
