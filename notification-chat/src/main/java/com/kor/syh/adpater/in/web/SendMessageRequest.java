package com.kor.syh.adpater.in.web;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class SendMessageRequest {

	private final String receiverId;
	private final String content;

}
