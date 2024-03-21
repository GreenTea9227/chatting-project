package com.kor.syh.common;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class PublishNotificationDto {
	private String id;
	private NotifyType type;
	private String senderId;
	private String content;
	private LocalDateTime time;
}
