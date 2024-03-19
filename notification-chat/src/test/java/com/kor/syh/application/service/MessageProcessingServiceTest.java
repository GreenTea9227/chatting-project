package com.kor.syh.application.service;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.kor.syh.adpater.in.channel.ReceiveMessage;
import com.kor.syh.application.port.in.notification.MessageType;
import com.kor.syh.application.port.in.notification.SendMessageCommand;
import com.kor.syh.application.port.out.channel.MessagePublishPort;
import com.kor.syh.application.port.out.channel.SendMessage;
import com.kor.syh.application.port.out.notification.NotificationPersistencePort;
import com.kor.syh.domain.NotifyType;

@SpringBootTest
class MessageProcessingServiceTest {

	@InjectMocks
	private MessageProcessingService messageProcessingService;

	@Mock
	private NotificationPersistencePort notificationPersistencePort;
	@Mock
	private MessagePublishPort messagePublishPort;

	@DisplayName("제대로 된 메시지 요청시 성공한다.")
	@Test
	void send_success_when_exact_message() {
		// given
		String receiverId = "receiverId";
		String senderId = "senderIdId";
		String content = "content";
		when(messagePublishPort.publish(eq(receiverId), any(SendMessage.class))).thenReturn(1L);

		// when
		SendMessageCommand sendMessageCommand = SendMessageCommand.of(MessageType.SEND, senderId, receiverId, content);
		Long send = messageProcessingService.send(sendMessageCommand);

		// then
		Assertions.assertThat(send).isEqualTo(1L);
		verify(messagePublishPort, times(1)).publish(eq(receiverId), any(SendMessage.class));
	}

	@DisplayName("정상 메시지는 받아서 sseEmitter로 알림을 보낸다")
	@Test
	void receive_success_when_correct_message() throws IOException {
		// given
		String receiverId = "receiverId";
		String data = "data";
		String senderId = "senderId";
		SseEmitter mock = mock(SseEmitter.class);

		when(notificationPersistencePort.findById(receiverId)).thenReturn(Optional.of(mock));
		doNothing().when(notificationPersistencePort).deleteById(receiverId);
		doNothing().when(mock).send((SseEmitter.SseEventBuilder)any());

		// when
		ReceiveMessage receiveMessage = ReceiveMessage.builder()
													  .id(senderId)
													  .type(NotifyType.NOTIFY)
													  .content(data)
													  .build();
		messageProcessingService.receive(receiverId, receiveMessage);

		// then
		verify(notificationPersistencePort, times(1)).findById(receiverId);
		verify(notificationPersistencePort, never()).deleteById(receiverId);
	}

	@DisplayName("잘못된 메시지는 받아서 예외를 터뜨린 후 sseEmitter를 삭제한다.")
	@Test
	void receive_fail_when_incorrect_message() throws IOException {
		// given
		String receiverId = "receiverId";
		String data = "data";
		String senderId = "senderId";
		SseEmitter mock = mock(SseEmitter.class);

		when(notificationPersistencePort.findById(receiverId)).thenReturn(Optional.of(mock));
		doNothing().when(notificationPersistencePort).deleteById(receiverId);
		doThrow(new IOException()).when(mock).send((SseEmitter.SseEventBuilder)any());

		// when
		ReceiveMessage receiveMessage = ReceiveMessage.builder()
													  .id(senderId)
													  .type(NotifyType.NOTIFY)
													  .content(data)
													  .build();
		Assertions.assertThatThrownBy(() ->
			messageProcessingService.receive(receiverId, receiveMessage)
		).isInstanceOf(RuntimeException.class);

		// then
		verify(notificationPersistencePort, times(1)).findById(receiverId);
		verify(mock, times(1)).send((SseEmitter.SseEventBuilder)any());
		verify(notificationPersistencePort, times(1)).deleteById(receiverId);
	}

}