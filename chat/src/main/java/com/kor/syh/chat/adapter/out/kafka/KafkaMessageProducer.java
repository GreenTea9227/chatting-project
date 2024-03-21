package com.kor.syh.chat.adapter.out.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.kor.syh.chat.application.port.out.kafka.KafkaMessageDto;
import com.kor.syh.chat.application.port.out.kafka.ProduceMessageBrokerPort;
import com.kor.syh.chat.domain.Message;
import com.kor.syh.common.utils.JsonUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class KafkaMessageProducer implements ProduceMessageBrokerPort {

	private final KafkaTemplate<String, Object> kafkaTemplate;

	@Override
	public void produce(String topic, Message message) {
		KafkaMessageDto kafkaMessageDto = new KafkaMessageDto(message.getRoomId(), message.getSenderId(),
			message.getContent(), message.getType());
		kafkaTemplate.send(topic, JsonUtil.classToString(kafkaMessageDto));
	}
}
