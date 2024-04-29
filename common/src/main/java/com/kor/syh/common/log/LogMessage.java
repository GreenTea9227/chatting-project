package com.kor.syh.common.log;

import lombok.Getter;

@Getter
public class LogMessage {
	private String timestamp;
	private String serviceId;
	private String logLevel;
	private String message;
	private String userId;
	private String requestId;
}
