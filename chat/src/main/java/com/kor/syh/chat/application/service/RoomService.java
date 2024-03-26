package com.kor.syh.chat.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.f4b6a3.tsid.TsidCreator;
import com.kor.syh.chat.adapter.in.web.RoomResponseDto;
import com.kor.syh.chat.application.port.in.ExitRoomUseCase;
import com.kor.syh.chat.application.port.in.HandlerRoomUseCase;
import com.kor.syh.chat.application.port.in.ParticipateRoomUseCase;
import com.kor.syh.chat.application.port.out.ManageRoomParticipantPort;
import com.kor.syh.chat.application.port.out.ManageRoomPort;
import com.kor.syh.chat.domain.Room;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class RoomService implements HandlerRoomUseCase, ParticipateRoomUseCase, ExitRoomUseCase {
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
		manageRoomParticipantPort.createRoom(roomId, userId);
		return RoomResponseDto.of(roomId);
	}

	@Override
	public void participate(String roomId, String userId) {
		manageRoomParticipantPort.participate(roomId, userId);
		//TODO 1.밀린 메시지 가져오기 2.notification 보내기
	}

	@Override
	public void exit(String roomId, String userId) {
		manageRoomParticipantPort.exit(roomId, userId);
	}

}
