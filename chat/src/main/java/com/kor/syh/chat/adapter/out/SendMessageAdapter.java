package com.kor.syh.chat.adapter.out;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.kor.syh.chat.adapter.in.web.MessageDto;
import com.kor.syh.chat.application.port.out.SendMessagePort;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class SendMessageAdapter implements SendMessagePort {

	private final SimpMessagingTemplate simpMessagingTemplate;
	@Override
	public void sendMessage(MessageDto messageDto) {
		simpMessagingTemplate.convertAndSend("/single/chat/" + messageDto.getRoomId(), messageDto);
	}
}
