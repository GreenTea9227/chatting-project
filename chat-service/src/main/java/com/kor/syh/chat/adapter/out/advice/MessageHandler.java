package com.kor.syh.chat.adapter.out.advice;

import java.security.Principal;

import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.kor.syh.chat.adapter.in.web.MessageDto;
import com.kor.syh.chat.application.service.UnauthorizedRoomAccessException;
import com.kor.syh.chat.domain.MessageType;
import com.kor.syh.config.message.UserPrincipalToken;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@ControllerAdvice
public class MessageHandler {

	private final SimpMessagingTemplate simpMessagingTemplate;

	@MessageExceptionHandler({UnauthorizedRoomAccessException.class, MessageDeliveryException.class})
	public void handleUnauthorizedRoomAccessException(Exception e, Principal principal) {
		log.error(e.getMessage());
		UserPrincipalToken userToken = (UserPrincipalToken)principal;
		MessageDto message = new MessageDto("server", "server", e.getMessage(), MessageType.SEND);
		String userName = userToken.getName();
		String roomId = userToken.getRoomId();

		simpMessagingTemplate.convertAndSendToUser(userName, "/single/" + roomId, message);
	}

}
