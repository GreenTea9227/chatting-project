package com.kor.syh.notification.adpater.in.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.kor.syh.notification.application.port.in.notification.NotificationUseCase;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class NotificationController {

	private final NotificationUseCase notificationUseCase;

	@ResponseStatus(value = HttpStatus.CREATED)
	@GetMapping(value = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public SseEmitter createSseEmitter(@RequestHeader(value = "X-Authorization-Id") String memberId) {
		return notificationUseCase.createNotificationChannel(memberId);
	}

}
