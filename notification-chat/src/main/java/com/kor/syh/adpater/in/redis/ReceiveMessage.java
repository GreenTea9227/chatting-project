package com.kor.syh.adpater.in.redis;

import java.time.LocalDateTime;

import com.kor.syh.domain.NotifyType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ReceiveMessage {
	private String id;
	private NotifyType type;
	private String sender;
	private String content;
	private LocalDateTime time;
}
