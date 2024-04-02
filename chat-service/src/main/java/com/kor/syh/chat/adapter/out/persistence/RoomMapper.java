package com.kor.syh.chat.adapter.out.persistence;

import org.springframework.stereotype.Component;

import com.kor.syh.chat.domain.Room;

@Component
public class RoomMapper {

	public Room toDomain(RoomDocument roomDocument) {

		return Room.builder()
				   .roomId(roomDocument.getRoomId())
				   .cratedDate(roomDocument.getCreateDate())
				   .updatedDate(roomDocument.getUpdateDate())
				   .build();
	}

	public RoomDocument toEntity(Room room) {
		return RoomDocument.builder()
						   .roomId(room.getRoomId())
						   .build();
	}
}
