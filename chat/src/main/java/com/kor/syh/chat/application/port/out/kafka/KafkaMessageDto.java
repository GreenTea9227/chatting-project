package com.kor.syh.chat.application.port.out.kafka;

import com.kor.syh.chat.domain.MessageType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class KafkaMessageDto {
	private String roomId;
	private String senderId;
	private String content;
	private MessageType type;
}
