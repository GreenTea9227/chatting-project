package com.kor.syh.chat.application.port.in;

import com.kor.syh.chat.adapter.in.web.MessageDto;

public interface SendMessageUseCase {
	void sendChat(MessageDto messageDto);
}
