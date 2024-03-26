package com.kor.syh.chat.adapter.out.persistence;

import org.springframework.stereotype.Component;

import com.kor.syh.chat.domain.Room;

@Component
public class RoomMapper {

	public Room mapToDomain(MongoRoom mongoRoom) {

		return Room.builder()
				   .roomId(mongoRoom.getRoomId())
				   .cratedDate(mongoRoom.getCreateDate())
				   .updatedDate(mongoRoom.getUpdateDate())
				   .build();
	}

	public MongoRoom mapToEntity(Room room) {
		return MongoRoom.builder()
						.roomId(room.getRoomId())
						.build();
	}
}
