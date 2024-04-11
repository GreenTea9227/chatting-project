package com.kor.syh.chat.application.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.f4b6a3.tsid.TsidCreator;
import com.kor.syh.chat.adapter.in.web.MessageDto;
import com.kor.syh.chat.application.port.in.HandleMessageUseCase;
import com.kor.syh.chat.application.port.out.RoomCachePort;
import com.kor.syh.chat.application.port.out.SaveMessagePort;
import com.kor.syh.chat.application.port.out.SendMessagePort;
import com.kor.syh.chat.application.port.out.kafka.ProduceMessageBrokerPort;
import com.kor.syh.chat.domain.Message;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class MessageService implements HandleMessageUseCase {

	private final ProduceMessageBrokerPort produceMessageBrokerPort;
	private final RoomCachePort roomPort;
	private final SaveMessagePort saveMessagePort;
	private final SendMessagePort sendMessagePort;

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
		sendMessagePort.sendMessage(messageDto);

		//notification TODO 알림 구현
		//sendNotificationPort.sendNotification(messageDto.getRoomId(), messageDto.getContent());

	}

	private Message createMessage(MessageDto messageDto) {
		String messageId = TsidCreator.getTsid().toString();
		LocalDateTime now = LocalDateTime.now();
		return Message.builder()
					  .messageId(messageId)
					  .content(messageDto.getContent())
					  .roomId(messageDto.getRoomId())
					  .senderId(messageDto.getSenderId())
					  .type(messageDto.getType())
					  .createdDate(now)
					  .build();
	}
}
