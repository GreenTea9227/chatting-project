package com.kor.syh.chat.adapter.out.persistence;

import java.time.LocalDateTime;

import com.kor.syh.chat.domain.MessageType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MongoMessage {
	private String messageId;
	private String senderId;
	private String content;
	private MessageType type;
	private LocalDateTime createdDate;
}
