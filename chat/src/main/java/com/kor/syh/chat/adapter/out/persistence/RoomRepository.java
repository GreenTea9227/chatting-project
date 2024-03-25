package com.kor.syh.chat.adapter.out.persistence;

import org.springframework.stereotype.Repository;

import com.kor.syh.chat.application.port.out.ManageRoomPort;
import com.kor.syh.chat.domain.Room;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class RoomRepository implements ManageRoomPort {
	private final SpringMongoRoomRepository springMongoRoomRepository;
	private final RoomMapper roomMapper;

	@Override
	public void saveRoom(Room room) {
		MongoRoom mongoRoom = roomMapper.mapToEntity(room);
		springMongoRoomRepository.save(mongoRoom);
	}

	@Override
	public void deleteRoom(String roomId) {
		springMongoRoomRepository.deleteById(roomId);
	}

}
