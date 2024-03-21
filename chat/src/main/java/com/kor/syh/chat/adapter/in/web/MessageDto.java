package com.kor.syh.chat.adapter.in.web;

import com.kor.syh.chat.domain.MessageType;

import lombok.Getter;

@Getter
public class MessageDto {
	private String roomId;
	private String senderId;
	private String content;
	private MessageType type;
}
