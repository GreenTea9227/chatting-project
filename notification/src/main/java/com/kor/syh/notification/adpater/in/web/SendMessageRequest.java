package com.kor.syh.notification.adpater.in.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class SendMessageRequest {

	private String receiverId;
	private String content;

}
