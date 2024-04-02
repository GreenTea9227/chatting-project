package com.kor.syh.notification;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.kor.syh.notification.application.exception.NotificationDeletionException;
import com.kor.syh.notification.application.exception.UnauthorizedAccessException;
import com.kor.syh.notification.application.port.out.CheckLoginMemberPort;
import com.kor.syh.notification.application.port.out.channel.MessageManagementPort;
import com.kor.syh.notification.application.port.out.notification.NotificationPersistencePort;
import com.kor.syh.notification.application.service.NotificationManagementService;

@ExtendWith(MockitoExtension.class)
class NotificationManagementServiceNotifyApplicationTests {
	@InjectMocks
	private NotificationManagementService notificationManagementService;

	@Mock
	private NotificationPersistencePort notificationPersistencePort;
	@Mock
	private MessageManagementPort messageManagementPort;
	@Mock
	private CheckLoginMemberPort checkLoginMemberPort;

	@DisplayName("로그인 한 사용자는 알림 채널 생성에 성공한다.")
	@Test
	void login_user_creates_notification_channel_success() {
		// given
		String memberId = "member123";
		when(checkLoginMemberPort.isLoginMember(memberId)).thenReturn(true);
		doNothing().when(notificationPersistencePort).save(eq(memberId), any(SseEmitter.class));
		doNothing().when(messageManagementPort).subscribe(memberId);
		messageManagementPort.subscribe(memberId);

		// when
		SseEmitter notificationChannel = notificationManagementService.createNotificationChannel(memberId);

		// then
		assertThat(notificationChannel).isNotNull();
	}

	@DisplayName("로그인 하지 않은 사용자는 알림 생성에 실패한다.")
	@Test
	void non_login_user_fails_to_create_notification() {
		// given
		String memberId = "member123";
		when(checkLoginMemberPort.isLoginMember(memberId)).thenReturn(false);

		// when, then
		assertThatThrownBy(() -> notificationManagementService.createNotificationChannel(memberId))
			.isInstanceOf(UnauthorizedAccessException.class);

	}

	@DisplayName("SseEmitter가 아무 문제 없이 삭제 된다.")
	@Test
	void sse_emitter_deleted_without_any_issue() {
		// given
		String memberId = "member123";
		doNothing().when(notificationPersistencePort).deleteById(memberId);
		doNothing().when(messageManagementPort).removeSubscribe(memberId);

		//when
		notificationManagementService.deleteNotificationChannel(memberId);

		//then
		verify(notificationPersistencePort, times(1)).deleteById(memberId);
		verify(messageManagementPort, times(1)).removeSubscribe(memberId);
	}

	@DisplayName("SseEmitter 삭제 시 문제가 생기면 custom 에외를 반환한다.")
	@Test
	void sse_emitter_deletion_returns_custom_exception_on_failure() {
		// given
		String memberId = "member123";

		//when
		doThrow(RuntimeException.class).when(notificationPersistencePort).deleteById(memberId);

		//then
		assertThatThrownBy(() -> notificationManagementService.deleteNotificationChannel(memberId))
			.isInstanceOf(NotificationDeletionException.class);

	}

}
