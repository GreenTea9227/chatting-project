package com.kor.syh.notification.adpater.in.web;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.kor.syh.notification.application.port.in.notification.NotificationUseCase;

@AutoConfigureWebTestClient
@WebMvcTest(NotificationController.class)
class NotificationControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private NotificationUseCase notificationUseCase;

	@Test
	void createSseEmitters() throws Exception {
		//given
		String memberId = "member123";
		SseEmitter sseEmitter = new SseEmitter();
		when(notificationUseCase.createNotificationChannel(memberId)).thenReturn(sseEmitter);

		//TODO sse test code 추가 작성
		//when
		ResultActions perform = mvc.perform(get("/notification/subscribe")
			.header("X-Authorization-Id", memberId));

		//then
		perform.andExpect(status().isCreated());

	}
}