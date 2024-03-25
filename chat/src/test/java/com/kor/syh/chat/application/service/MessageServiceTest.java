package com.kor.syh.chat.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.kor.syh.chat.adapter.in.web.MessageDto;
import com.kor.syh.chat.application.port.out.ManageRoomParticipantPort;
import com.kor.syh.chat.application.port.out.SaveMessagePort;
import com.kor.syh.chat.application.port.out.SendMessagePort;
import com.kor.syh.chat.application.port.out.kafka.ProduceMessageBrokerPort;
import com.kor.syh.chat.domain.Message;
import com.kor.syh.chat.domain.MessageType;

@ExtendWith(MockitoExtension.class)
class MessageServiceTest {

	@InjectMocks
	private MessageService messageService;
	@Mock
	private ProduceMessageBrokerPort produceMessageBrokerPort;
	@Mock
	private SaveMessagePort saveMessagePort;
	@Mock
	private ManageRoomParticipantPort roomPort;

	@Mock
	private SendMessagePort sendMessagePort;

	@DisplayName("방의 멤버라면 메시지를 보낼 수 있다.")
	@Test
	void success_send_message_if_a_room_participant() {
		// given
		String roomId = "roomId";
		String userId = "userId";
		String message = "message";
		MessageDto messageDto = new MessageDto(roomId, userId, message, MessageType.SEND);

		when(roomPort.isChatRoomExists(roomId)).thenReturn(true);
		when(roomPort.isRoomParticipant(roomId, userId)).thenReturn(true);

		doNothing().when(saveMessagePort).save(eq(roomId), any(Message.class));
		doNothing().when(produceMessageBrokerPort).produce(any(Message.class));

		// when
		messageService.publishMessage(messageDto);

		// then
		verify(roomPort, times(1)).isRoomParticipant(roomId, userId);
		verify(saveMessagePort, times(1)).save(eq(roomId), any(Message.class));
		verify(produceMessageBrokerPort, times(1)).produce(any(Message.class));
	}

	@DisplayName("방의 멤버가 아니라면 메시지를 보낼 수 없다.")
	@Test
	void fail_send_message_if_not_a_room_participant() {
		// given
		String roomId = "roomId";
		String userId = "userId";
		String message = "message";
		MessageDto messageDto = new MessageDto(roomId, userId, message, MessageType.SEND);

		when(roomPort.isChatRoomExists(roomId)).thenReturn(true);
		when(roomPort.isRoomParticipant(roomId, userId)).thenReturn(false);

		// when, then
		assertThatThrownBy(() -> messageService.publishMessage(messageDto))
			.isInstanceOf(UnauthorizedRoomAccessException.class);

	}

	@DisplayName("방이 개설되지 않았다면 메시지를 보낼 수 없다.")
	@Test
	void fail_to_send_message_if_room_not_found() {
		// given
		String roomId = "roomId";
		String userId = "userId";
		String message = "message";
		MessageDto messageDto = new MessageDto(roomId, userId, message, MessageType.SEND);

		when(roomPort.isChatRoomExists(roomId)).thenReturn(false);

		// when, then
		assertThatThrownBy(() -> messageService.publishMessage(messageDto))
			.isInstanceOf(UnauthorizedRoomAccessException.class);

	}

	@DisplayName("메시지 브로커를 통해 메시지가 정상적으로 도착하면 해당 방으로 메시지를 보낸다.")
	@Test
	void send_message_to_room_from_broker() {
		// given
		String roomId = "roomId";
		String userId = "userId";
		String message = "message";
		MessageDto messageDto = new MessageDto(roomId, userId, message, MessageType.SEND);
		when(roomPort.isParticipatingNow(roomId, userId)).thenReturn(true);
		doNothing().when(sendMessagePort).sendMessage(messageDto);

		// when
		messageService.sendMessageToUser(messageDto);

		// then
		verify(roomPort, times(1)).isParticipatingNow(roomId, userId);
		verify(sendMessagePort, times(1)).sendMessage(messageDto);

	}

}