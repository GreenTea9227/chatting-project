package com.kor.syh.chat.application.port.in;

import org.springframework.data.domain.Slice;

import com.kor.syh.chat.adapter.in.web.PageDto;
import com.kor.syh.chat.adapter.in.web.RoomResponseDto;
import com.kor.syh.chat.application.port.out.RoomsDto;

public interface HandlerRoomUseCase {
	RoomResponseDto createRoom(String userId);

	void enterRoom(String roomId, String userId);

	Slice<RoomsDto> getRooms(PageDto pageable);
}
