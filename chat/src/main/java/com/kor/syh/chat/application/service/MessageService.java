package com.kor.syh.chat.application.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.f4b6a3.tsid.TsidCreator;
import com.kor.syh.chat.adapter.in.web.MessageDto;
import com.kor.syh.chat.application.port.in.SendMessageUseCase;
import com.kor.syh.chat.application.port.out.ManageMessagePort;
import com.kor.syh.chat.application.port.out.ManageRoomParticipantPort;
import com.kor.syh.chat.application.port.out.kafka.ProduceMessageBrokerPort;
import com.kor.syh.chat.domain.Message;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class MessageService implements SendMessageUseCase {

	private final ProduceMessageBrokerPort produceMessageBrokerPort;
	private final ManageMessagePort manageMessagePort;
	private final ManageRoomParticipantPort roomPort;

	@Override
	public void sendChat(MessageDto messageDto) {

		String roomId = messageDto.getRoomId();
		String senderId = messageDto.getSenderId();

		if (!roomPort.isChatRoomExists(roomId)) {
			throw new UnauthorizedRoomAccessException("방이 존재하지 않습니다.");
		}

		if (!roomPort.isRoomParticipant(roomId, senderId)) {
			throw new UnauthorizedRoomAccessException("%s는 %s에 메시지를 보낼 권한이 없습니다.".formatted(roomId, senderId));
		}

		String messageId = TsidCreator.getTsid().toString();
		LocalDateTime now = LocalDateTime.now();
		Message message = Message.builder()
								 .messageId(messageId)
								 .content(messageDto.getContent())
								 .createdDate(now)
								 .roomId(roomId)
								 .senderId(senderId)
								 .type(messageDto.getType())
								 .build();

		manageMessagePort.save(message);
		produceMessageBrokerPort.produce(message);
	}
}
