package com.kor.syh.domain;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Notify {

	private final String id;
	private final String sender;
	private final String receiver;
	private final String content;
	private final LocalDateTime time;

}
