package com.kor.syh.chat.application.port.in;

import com.kor.syh.chat.adapter.in.web.RoomResponseDto;

public interface HandlerRoomUseCase {
	RoomResponseDto createRoom(String userId);

}
