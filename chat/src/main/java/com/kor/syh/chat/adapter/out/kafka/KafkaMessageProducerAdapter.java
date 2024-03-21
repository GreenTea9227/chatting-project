package com.kor.syh.chat.adapter.out.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.kor.syh.chat.application.port.out.kafka.KafkaMessageDto;
import com.kor.syh.chat.application.port.out.kafka.ProduceMessageBrokerPort;
import com.kor.syh.chat.domain.Message;
import com.kor.syh.common.utils.JsonUtil;
import com.kor.syh.config.kafka.KafkaConstant;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class KafkaMessageProducerAdapter implements ProduceMessageBrokerPort {

	private final KafkaTemplate<String, Object> kafkaTemplate;
	private final KafkaConstant kafkaConstant;

	@Override
	public void produce(Message message) {
		KafkaMessageDto kafkaMessageDto = new KafkaMessageDto(message.getRoomId(), message.getSenderId(),
			message.getContent(), message.getType());
		kafkaTemplate.send(kafkaConstant.TOPIC_ID, JsonUtil.classToString(kafkaMessageDto));
	}
}
