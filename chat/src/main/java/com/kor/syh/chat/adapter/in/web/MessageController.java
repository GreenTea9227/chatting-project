package com.kor.syh.chat.adapter.in.web;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import com.kor.syh.chat.application.port.in.HandleMessageUseCase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MessageController {

	private final HandleMessageUseCase handleMessageUseCase;

	@MessageMapping("/chat/{roomId}")
	public void send(@DestinationVariable("roomId") String roomId, MessageDto message) {

		log.info("roomId: {} , send message : {}", roomId, message);

		handleMessageUseCase.publishMessage(message);

	}
}
