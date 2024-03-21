package com.kor.syh.chat.application.port.out;

public interface ManageRoomParticipantPort {
	boolean isRoomParticipant(String roomId, String userId);

	boolean isParticipatingNow(String roomId, String userId);

	boolean isChatRoomExists(String roomId);
}
