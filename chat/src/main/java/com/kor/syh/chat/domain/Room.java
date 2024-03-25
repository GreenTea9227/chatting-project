package com.kor.syh.chat.domain;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class Room {
	private String roomId;
	private String creatorId;
	private LocalDateTime cratedDate;
	private LocalDateTime updatedDate;
}
