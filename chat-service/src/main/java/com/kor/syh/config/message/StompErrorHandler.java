package com.kor.syh.config.message;

import java.nio.charset.StandardCharsets;

import org.jetbrains.annotations.NotNull;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.StompSubProtocolErrorHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kor.syh.common.CommonResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class StompErrorHandler extends StompSubProtocolErrorHandler {

	private final ObjectMapper objectMapper;

	@Override
	public Message<byte[]> handleClientMessageProcessingError(Message<byte[]> clientMessage, Throwable ex) {
		try {
			return createErrorMessage(ex);
		} catch (Exception e) {
			log.error("STOMP error handling failed", e);
			return super.handleClientMessageProcessingError(clientMessage, e);
		}
	}

	private @NotNull Message<byte[]> createErrorMessage(Throwable ex) throws
		JsonProcessingException {

		StompHeaderAccessor accessor = StompHeaderAccessor.create(StompCommand.ERROR);
		accessor.setLeaveMutable(true);
		CommonResponse<?> commonResponse = CommonResponse.fail(ex.getCause().getMessage());
		String message = objectMapper.writeValueAsString(commonResponse);

		return MessageBuilder.createMessage(message.getBytes(StandardCharsets.UTF_8), accessor.getMessageHeaders());
	}

}
