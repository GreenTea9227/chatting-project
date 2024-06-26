package com.kor.syh.loggingservice.domain;

import java.time.Instant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class LogMessage {
	private MessageLevel type;
	private String userId;
	private String message;
	private String ipAddress;
	private Instant timestamp;
}
