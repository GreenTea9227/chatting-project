package com.kor.syh.chat.application.port.out;

public interface RoomCachePort {
	void createRoom(String roomId, String userId);

	void enterRoom(String roomId, String userId);

	void exit(String roomId, String userId);

	boolean isRoomParticipant(String roomId, String userId);

	boolean isParticipatingNow(String roomId, String userId);

	boolean isChatRoomExists(String roomId);

}
