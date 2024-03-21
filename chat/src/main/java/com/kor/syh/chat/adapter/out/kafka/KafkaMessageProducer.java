package com.kor.syh.chat.adapter.out.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.kor.syh.chat.application.port.out.kafka.ProduceMessageBrokerPort;
import com.kor.syh.chat.domain.Chat;
import com.kor.syh.common.utils.JsonUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class KafkaMessageProducer implements ProduceMessageBrokerPort {

	private final KafkaTemplate<String, Object> kafkaTemplate;

	@Override
	public void produce(String topic, Chat message) {
		kafkaTemplate.send(topic, JsonUtil.classToString(message));
	}
}
