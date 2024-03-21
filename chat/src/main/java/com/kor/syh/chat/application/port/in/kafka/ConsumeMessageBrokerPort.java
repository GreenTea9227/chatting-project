package com.kor.syh.chat.application.port.in.kafka;

import com.kor.syh.chat.domain.Chat;

public interface ConsumeMessageBrokerPort {
	void consume( Chat message);
}
