package com.kor.syh.application.port.out.channel;

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
public class SendMessage {
	private String id;
	private NotifyType type;
	private String sender;
	private String receiver;
	private String content;
	private LocalDateTime time;
}
