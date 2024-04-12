package com.kor.syh.websocket;

import static java.util.concurrent.TimeUnit.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.ConnectionLostException;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.kor.syh.chat.adapter.in.web.MessageDto;
import com.kor.syh.chat.application.port.in.HandleMessageUseCase;
import com.kor.syh.chat.domain.MessageType;
import com.kor.syh.common.UnitTest;
import com.kor.syh.common.jwt.JwtUtils;

@UnitTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StompTest {

	@MockBean
	private JwtUtils jwtUtils;

	@MockBean
	private HandleMessageUseCase handleMessageUseCase;

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@LocalServerPort
	private int port;

	private final String SEND_MESSAGE_ENDPOINT = "/send/chat";
	private final String SUBSCRIBE_ENDPOINT = "/single";
	private String URL;

	WebSocketStompClient stompClient;
	CompletableFuture<MessageDto> arriveMessage;
	StompSessionHandlerAdapter handler;

	@BeforeEach
	void setUp() {
		URL = "ws://localhost:%d/ws".formatted(port);
		arriveMessage = new CompletableFuture<>();
		// websocket, sockjs, stomp 준비, 직렬화 설정
		stompClient = new WebSocketStompClient(
			new SockJsClient(List.of(new WebSocketTransport(new StandardWebSocketClient()))));

		MappingJackson2MessageConverter messageConverter = new MappingJackson2MessageConverter();
		ObjectMapper objectMapper = messageConverter.getObjectMapper();
		objectMapper.registerModules(new JavaTimeModule(), new ParameterNamesModule());
		stompClient.setMessageConverter(messageConverter);

		// stomp message handler
		handler = new StompSessionHandlerAdapter() {
			@Override
			public Type getPayloadType(StompHeaders headers) {
				return MessageDto.class;
			}

			@Override
			public void handleFrame(StompHeaders headers, Object payload) {
				arriveMessage.complete((MessageDto)payload);
			}
		};
	}

	@DisplayName("stomp 연결 테스트")
	@Nested
	class ConnectStomp {

		@DisplayName("권한이 있다면 연결에 성공한다.")
		@Test
		void success_connect_stomp() throws ExecutionException, InterruptedException, TimeoutException {
			// given
			when(jwtUtils.isValidToken(any())).thenReturn(true);
			when(jwtUtils.parseMemberIdFromToken(any())).thenReturn("userIdFromToken");

			StompHeaders headers = new StompHeaders();
			headers.add("Authorization", "Bearer mytoken");
			StompSession stompSession = stompClient.connectAsync(URL, new WebSocketHttpHeaders(),
													   headers,
													   new StompSessionHandlerAdapter() {
													   })
												   .get(1, SECONDS);

			//when
			stompSession.subscribe(SUBSCRIBE_ENDPOINT, handler);

			//then
			assertThat(stompSession.isConnected()).isTrue();
			verify(jwtUtils, times(1)).isValidToken(any());
			verify(jwtUtils, times(1)).parseMemberIdFromToken(any());
		}

		@DisplayName("적절한 권한이 없다면 연결에 실패한다.")
		@Test
		void fail_connect_stomp() {
			// given
			when(jwtUtils.isValidToken(any())).thenReturn(false);

			StompHeaders headers = new StompHeaders();
			headers.add("Authorization", "Bearer mytoken");

			CompletableFuture<StompSession> stompSessionCompletableFuture = stompClient.connectAsync(URL,
				new WebSocketHttpHeaders(),
				headers,
				new StompSessionHandlerAdapter() {
				});

			// when, then
			assertThatThrownBy(() -> stompSessionCompletableFuture.get(1, SECONDS))
				.isInstanceOf(ExecutionException.class)
				.hasCauseInstanceOf(ConnectionLostException.class);
			verify(jwtUtils, times(1)).isValidToken(any());
		}

	}

	@DisplayName("message send")
	@Test
	void success_message_send() throws ExecutionException, InterruptedException, TimeoutException {
		// given
		when(jwtUtils.isValidToken(any())).thenReturn(true);
		when(jwtUtils.parseMemberIdFromToken(any())).thenReturn("userIdFromToken");

		doAnswer(invocation -> {
			simpMessagingTemplate.convertAndSend(SUBSCRIBE_ENDPOINT, new MessageDto(
				"roomId", "senderId", "content", MessageType.SEND
			));
			return null;
		}).when(handleMessageUseCase).publishMessage(any(MessageDto.class));

		StompHeaders headers = new StompHeaders();
		headers.add("Authorization", "Bearer mytoken");
		StompSession stompSession = stompClient.connectAsync(URL, new WebSocketHttpHeaders(),
												   headers,
												   new StompSessionHandlerAdapter() {
												   })
											   .get(1, SECONDS);
		stompSession.subscribe(SUBSCRIBE_ENDPOINT, handler);

		//when
		stompSession.send(SEND_MESSAGE_ENDPOINT + "/roomId",
			new MessageDto("secondRoom", "123", "hello", MessageType.SEND));

		//then
		assertThat(stompSession.isConnected()).isTrue();
		verify(jwtUtils, times(1)).isValidToken(any());
		verify(jwtUtils, times(1)).parseMemberIdFromToken(any());
		verify(handleMessageUseCase, times(1)).publishMessage(any());
	}

}
