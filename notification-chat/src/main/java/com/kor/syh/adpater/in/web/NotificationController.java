package com.kor.syh.adpater.in.web;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.kor.syh.CommonResponse;
import com.kor.syh.application.port.in.MessageType;
import com.kor.syh.application.port.in.NotificationUseCase;
import com.kor.syh.application.port.in.SendMessageCommand;
import com.kor.syh.application.port.in.SendNotificationUseCase;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/notification")
@Controller
public class NotificationController {

	private final NotificationUseCase notificationUseCase;
	private final SendNotificationUseCase sendNotificationUseCase;

	@GetMapping(value = "/create", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public SseEmitter createSseEmitter() {
		SseEmitter sseEmitter = notificationUseCase.createNotification("1");
		return sseEmitter;
	}

	@PostMapping("/send")
	public CommonResponse<?> sendNotification(@RequestBody SendMessageRequest sendMessageRequest) {
		// TODO senderId 수정 필요
		sendNotificationUseCase.send(SendMessageCommand.of(MessageType.SEND,
			"1",
			sendMessageRequest.getReceiverId(),
			sendMessageRequest.getContent()
		));
		return CommonResponse.success("success send message");
	}

}
