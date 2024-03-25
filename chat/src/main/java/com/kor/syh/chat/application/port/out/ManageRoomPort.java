package com.kor.syh.chat.application.port.out;

import com.kor.syh.chat.domain.Room;

public interface ManageRoomPort {
	void saveRoom(Room room);
	void deleteRoom(String roomId);
}
