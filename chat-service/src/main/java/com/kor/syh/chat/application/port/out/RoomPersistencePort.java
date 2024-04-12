package com.kor.syh.chat.application.port.out;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import com.kor.syh.chat.domain.Room;

public interface RoomPersistencePort {
	void saveRoom(Room room);

	void deleteRoom(String roomId);

	void addParticipant(String roomId, String userId);

	void exitRoom(String roomId, String userId);

	Slice<RoomsDto> findRoomsByPageable(Pageable pageable);
}
