package com.kor.syh.chat.application.port.in.kafka;

import com.kor.syh.chat.domain.Message;

public interface ConsumeMessageBrokerPort {
	void consume( Message message);
}
