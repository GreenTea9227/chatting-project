package com.kor.syh.chat.application.port.out;

public interface ManageRoomParticipantPort {
	void createRoom(String roomId, String userId);

	void participate(String roomId, String userId);

	void exit(String roomId, String userId);

	boolean isRoomParticipant(String roomId, String userId);

	boolean isParticipatingNow(String roomId, String userId);

	boolean isChatRoomExists(String roomId);

}
