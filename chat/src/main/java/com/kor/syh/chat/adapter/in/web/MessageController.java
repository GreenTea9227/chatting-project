package com.kor.syh.chat.adapter.in.web;

import java.security.Principal;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import com.kor.syh.chat.application.port.in.ExitRoomUseCase;
import com.kor.syh.chat.application.port.in.HandleMessageUseCase;
import com.kor.syh.chat.application.port.in.ParticipateRoomUseCase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MessageController {

	private final HandleMessageUseCase handleMessageUseCase;
	private final ParticipateRoomUseCase participateRoomUseCase;
	private final ExitRoomUseCase exitRoomUseCase;

	@MessageMapping("/chat/{roomId}")
	public void send(@DestinationVariable("roomId") String roomId, MessageDto message) {

		log.info("roomId: {} , send message : {}", roomId, message);
		handleMessageUseCase.publishMessage(message);
	}

	@MessageMapping("/enter/{roomId}")
	public void enter(@DestinationVariable("roomId") String roomId, MessageDto message, Principal principal) {

		log.info("roomId: {} , enter message : {}", roomId, message);
		participateRoomUseCase.participate(roomId, principal.getName());
		handleMessageUseCase.publishMessage(message);
	}

	@MessageMapping("/exit/{roomId}")
	public void exit(@DestinationVariable("roomId") String roomId, MessageDto message, Principal principal) {

		log.info("roomId: {} , exit message : {}", roomId, message);
		exitRoomUseCase.exit(roomId, principal.getName());
		handleMessageUseCase.publishMessage(message);
	}

}
