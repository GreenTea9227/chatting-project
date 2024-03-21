package com.kor.syh.chat.application.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.f4b6a3.tsid.TsidCreator;
import com.kor.syh.chat.adapter.in.web.MessageDto;
import com.kor.syh.chat.application.port.in.SendMessageUseCase;
import com.kor.syh.chat.application.port.out.ManageMessagePort;
import com.kor.syh.chat.application.port.out.kafka.ProduceMessageBrokerPort;
import com.kor.syh.chat.domain.Message;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class MessageService implements SendMessageUseCase {

	private final ProduceMessageBrokerPort produceMessageBrokerPort;
	private final ManageMessagePort manageMessagePort;

	@Override
	public void sendChat(MessageDto messageDto) {
		Message message = Message.builder()
								 .messageId(TsidCreator.getTsid().toString())
								 .content(messageDto.getContent())
								 .createdDate(LocalDateTime.now())
								 .roomId(messageDto.getRoomId())
								 .senderId(messageDto.getSenderId()).type(messageDto.getType())
								 .build();

		manageMessagePort.save(message);
		produceMessageBrokerPort.produce(message);
	}
}
