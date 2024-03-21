package com.kor.syh.chat.application.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.f4b6a3.tsid.TsidCreator;
import com.kor.syh.chat.adapter.in.web.MessageDto;
import com.kor.syh.chat.application.port.in.HandleMessageUseCase;
import com.kor.syh.chat.application.port.out.ManageRoomParticipantPort;
import com.kor.syh.chat.application.port.out.SaveMessagePort;
import com.kor.syh.chat.application.port.out.SendMessagePort;
import com.kor.syh.chat.application.port.out.SendNotificationPort;
import com.kor.syh.chat.application.port.out.kafka.ProduceMessageBrokerPort;
import com.kor.syh.chat.domain.Message;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class MessageService implements HandleMessageUseCase {

	private final ProduceMessageBrokerPort produceMessageBrokerPort;
	private final ManageRoomParticipantPort roomPort;
	private final SaveMessagePort saveMessagePort;
	private final SendMessagePort sendMessagePort;
	private final SendNotificationPort sendNotificationPort;

	@Override
	public void publishMessage(MessageDto messageDto) {

		String roomId = messageDto.getRoomId();
		String senderId = messageDto.getSenderId();

		if (!roomPort.isChatRoomExists(roomId)) {
			throw new UnauthorizedRoomAccessException("방이 존재하지 않습니다.");
		}

		if (!roomPort.isRoomParticipant(roomId, senderId)) {
			throw new UnauthorizedRoomAccessException("%s는 %s에 메시지를 보낼 권한이 없습니다.".formatted(roomId, senderId));
		}

		Message message = createMessage(messageDto);

		saveMessagePort.save(message);
		produceMessageBrokerPort.produce(message);
	}

	@Override
	public void sendMessageToUser(MessageDto messageDto) {
		String roomId = messageDto.getRoomId();
		String senderId = messageDto.getSenderId();

		if (roomPort.isParticipatingNow(roomId, senderId)) {
			//send message
			sendMessagePort.sendMessage(messageDto);
		} else {
			//notification TODO
			//sendNotificationPort.sendNotification(messageDto.getRoomId(), messageDto.getContent());
		}

	}

	private Message createMessage(MessageDto messageDto) {
		String messageId = TsidCreator.getTsid().toString();
		LocalDateTime now = LocalDateTime.now();
		return Message.builder()
					  .messageId(messageId)
					  .content(messageDto.getContent())
					  .createdDate(now)
					  .roomId(messageDto.getRoomId())
					  .senderId(messageDto.getSenderId())
					  .type(messageDto.getType())
					  .build();
	}
}
