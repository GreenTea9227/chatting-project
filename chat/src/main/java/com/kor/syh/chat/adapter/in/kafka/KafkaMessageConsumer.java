package com.kor.syh.chat.adapter.in.kafka;

import org.apache.kafka.common.protocol.types.Field;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.kor.syh.chat.application.port.in.kafka.ConsumeMessageBrokerPort;
import com.kor.syh.chat.application.port.out.kafka.KafkaMessageDto;
import com.kor.syh.chat.domain.Message;
import com.kor.syh.common.utils.JsonUtil;
import com.kor.syh.config.KafkaConstant;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaMessageConsumer implements ConsumeMessageBrokerPort {

	private final SimpMessagingTemplate simpMessagingTemplate;

	@Override
	@KafkaListener(topics = KafkaConstant.TOPIC_ID, groupId = KafkaConstant.GROUP_ID)
	public void consume(String message) {
		KafkaMessageDto kafkaMessageDto = JsonUtil.stringToClass(message, KafkaMessageDto.class);
		log.info(" message: {}", kafkaMessageDto);
		simpMessagingTemplate.convertAndSend("/single/chat/" + kafkaMessageDto.getRoomId(), kafkaMessageDto);
	}
}
