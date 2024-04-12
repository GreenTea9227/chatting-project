package com.kor.syh.chat.application.port.out;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RoomsDto {

	private String roomId;
	private List<String> participants;
	private LocalDateTime createDate;
	private LocalDateTime updateDate;
}
