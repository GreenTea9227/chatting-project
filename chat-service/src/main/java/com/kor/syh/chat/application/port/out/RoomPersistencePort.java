package com.kor.syh.chat.application.port.out;

import com.kor.syh.chat.domain.Room;

public interface RoomPersistencePort {
	void saveRoom(Room room);

	void deleteRoom(String roomId);

	void addParticipant(String roomId, String userId);

	void exitRoom(String roomId, String userId);
}
