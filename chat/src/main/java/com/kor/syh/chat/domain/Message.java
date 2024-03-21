package com.kor.syh.chat.domain;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class Message {
	private String messageId;
	private String roomId;
	private String senderId;
	private String content;
	private MessageType type;
	private LocalDateTime createdDate;

}
