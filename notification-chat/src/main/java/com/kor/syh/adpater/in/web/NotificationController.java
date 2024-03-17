package com.kor.syh.adpater.in.web;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.kor.syh.application.port.in.NotificationUseCase;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/notification")
@Controller
public class NotificationController {

	private final NotificationUseCase notificationUseCase;

	@GetMapping(value = "/create", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public SseEmitter createSseEmitter() {
		SseEmitter sseEmitter = notificationUseCase.createNotification("1");
		return sseEmitter;
	}

}
