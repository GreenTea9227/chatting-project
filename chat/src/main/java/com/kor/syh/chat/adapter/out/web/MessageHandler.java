package com.kor.syh.chat.adapter.out.web;

import java.security.Principal;

import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.kor.syh.chat.adapter.in.web.MessageDto;
import com.kor.syh.chat.application.service.UnauthorizedRoomAccessException;
import com.kor.syh.chat.domain.MessageType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MessageHandler {

	private final SimpMessagingTemplate simpMessagingTemplate;

	@MessageExceptionHandler(UnauthorizedRoomAccessException.class)
	public void handleUnauthorizedRoomAccessException(UnauthorizedRoomAccessException e, Principal principal) {
		log.error(e.getMessage());
		MessageDto message = new MessageDto("server","server",e.getMessage(), MessageType.SEND);

		//TODO 에러 처리
		simpMessagingTemplate.convertAndSendToUser(principal.getName(),"/single/chat",message );
	}
}
