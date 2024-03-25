package com.kor.syh.chat.adapter.out.persistence;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Document
public class MongoRoom {
	@Id
	private String roomId;
	private List<MongoMessage> messages;
	private LocalDateTime createDate;
	private LocalDateTime updateDate;
}
