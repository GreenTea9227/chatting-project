package com.kor.syh.chat.adapter.in.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.kor.syh.chat.application.port.in.kafka.ConsumeMessageBrokerPort;
import com.kor.syh.chat.domain.Message;
import com.kor.syh.config.KafkaConstant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KafkaMessageConsumer implements ConsumeMessageBrokerPort {
	@Override
	@KafkaListener(topics = KafkaConstant.TOPIC_ID, groupId = KafkaConstant.GROUP_ID)
	public void consume(Message message) {
		log.info(" message: {}", message);
	}
}
