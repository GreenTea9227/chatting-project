package com.kor.syh.chat.adapter.in.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.kor.syh.chat.adapter.in.web.MessageDto;
import com.kor.syh.chat.application.port.in.HandleMessageUseCase;
import com.kor.syh.chat.application.port.in.kafka.ConsumeMessageBrokerPort;
import com.kor.syh.chat.application.port.out.kafka.KafkaMessageDto;
import com.kor.syh.common.utils.JsonUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaMessageConsumerAdapter implements ConsumeMessageBrokerPort {

	private final HandleMessageUseCase handleMessageUseCase;

	@Override
	@KafkaListener(topics = "chat")
	public void consume(String message) {
		KafkaMessageDto kafkaMessageDto = JsonUtil.stringToClass(message, KafkaMessageDto.class);
		log.info("message: {}", kafkaMessageDto);
		MessageDto messageDto = new MessageDto(
			kafkaMessageDto.getRoomId(),
			kafkaMessageDto.getSenderId(),
			kafkaMessageDto.getContent(),
			kafkaMessageDto.getType()
		);
		handleMessageUseCase.sendMessageToUser(messageDto);
	}
}
