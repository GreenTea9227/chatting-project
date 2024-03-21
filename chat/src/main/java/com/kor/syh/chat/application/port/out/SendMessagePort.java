package com.kor.syh.chat.application.port.out;

import com.kor.syh.chat.adapter.in.web.MessageDto;

public interface SendMessagePort {
	void sendMessage(MessageDto message);
}
