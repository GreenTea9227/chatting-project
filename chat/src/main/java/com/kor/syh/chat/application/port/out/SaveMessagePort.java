package com.kor.syh.chat.application.port.out;

import com.kor.syh.chat.domain.Message;

public interface SaveMessagePort {
	void save(String roomId, Message message);
}
