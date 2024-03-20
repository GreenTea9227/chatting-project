package com.kor.syh.notification.domain;

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
public class Notify {

	private String id;
	private NotifyType type;
	private String sender;
	private String receiver;
	private String content;
	private LocalDateTime time;

}
