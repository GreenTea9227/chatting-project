package com.kor.syh.chat.adapter.out.adapter;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
@Document
public class MongoMessage {
	@Id
	private String messageId;
	private String roomId;
	private String senderId;
	private String content;
	private MessageType type;
	private LocalDateTime createdDate;
}
