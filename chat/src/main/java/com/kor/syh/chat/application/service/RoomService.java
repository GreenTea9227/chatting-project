package com.kor.syh.chat.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.f4b6a3.tsid.TsidCreator;
import com.kor.syh.chat.adapter.out.web.RoomResponseDto;
import com.kor.syh.chat.application.port.in.HandlerRoomUseCase;
import com.kor.syh.chat.application.port.out.ManageRoomParticipantPort;
import com.kor.syh.chat.application.port.out.ManageRoomPort;
import com.kor.syh.chat.domain.Room;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class RoomService implements HandlerRoomUseCase {
	private final ManageRoomPort manageRoomPort;
	private final ManageRoomParticipantPort manageRoomParticipantPort;
	@Override
	public RoomResponseDto createRoom(String userId) {
		String roomId = TsidCreator.getTsid().toString();
		Room room = Room.builder()
						.roomId(roomId)
						.creatorId(userId)
						.build();
		manageRoomPort.saveRoom(room);
		manageRoomParticipantPort.createRoom(roomId,userId);
		return RoomResponseDto.of(roomId);

	}
}
