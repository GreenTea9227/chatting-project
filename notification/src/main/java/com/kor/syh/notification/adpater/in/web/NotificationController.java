package com.kor.syh.notification.adpater.in.web;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.kor.syh.cmmon.CommonResponse;
import com.kor.syh.notification.application.port.in.notification.MessageType;
import com.kor.syh.notification.application.port.in.notification.NotificationUseCase;
import com.kor.syh.notification.application.port.in.notification.SendMessageCommand;
import com.kor.syh.notification.application.port.out.notification.SendNotificationUseCase;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/notification")
@RestController
public class NotificationController {

	private final NotificationUseCase notificationUseCase;
	private final SendNotificationUseCase sendNotificationUseCase;

	@GetMapping(value = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public SseEmitter createSseEmitter() {
		SseEmitter sseEmitter = notificationUseCase.createNotification("1");
		return sseEmitter;
	}

	//TODO 비동기 통신을 이용할 수 있도록 조치 필요
	@PostMapping("/send")
	public CommonResponse<?> sendNotification(@RequestBody SendMessageRequest sendMessageRequest) {

		Long result = sendNotificationUseCase.send(SendMessageCommand.of(MessageType.SEND,
			"1",
			sendMessageRequest.getReceiverId(),
			sendMessageRequest.getContent()
		));

		return result == 1 ? CommonResponse.success("success send message") : CommonResponse.fail("no user");
	}

}
