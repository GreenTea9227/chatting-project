package com.kor.syh.notification.application.port.in.notification;

import com.kor.syh.cmmon.SelfValidating;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class SendMessageCommand extends SelfValidating<SendMessageCommand> {

	private final MessageType type;
	private final String senderId;
	private final String receiverId;
	private final String content;

}
