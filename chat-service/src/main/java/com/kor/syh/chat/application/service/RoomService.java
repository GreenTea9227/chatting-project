package com.kor.syh.chat.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.f4b6a3.tsid.TsidCreator;
import com.kor.syh.chat.adapter.in.web.RoomResponseDto;
import com.kor.syh.chat.application.port.in.ExitRoomUseCase;
import com.kor.syh.chat.application.port.in.HandlerRoomUseCase;
import com.kor.syh.chat.application.port.in.ParticipateRoomUseCase;
import com.kor.syh.chat.application.port.out.RoomCachePort;
import com.kor.syh.chat.application.port.out.RoomPersistencePort;
import com.kor.syh.chat.domain.Room;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class RoomService implements HandlerRoomUseCase, ParticipateRoomUseCase, ExitRoomUseCase {
	private final RoomPersistencePort roomPersistencePort;
	private final RoomCachePort roomCachePort;

	@Override
	public RoomResponseDto createRoom(String userId) {
		String roomId = TsidCreator.getTsid().toString();
		Room room = Room.builder()
						.roomId(roomId)
						.creatorId(userId)
						.build();
		roomPersistencePort.saveRoom(room);
		roomCachePort.createRoom(roomId, userId);
		return RoomResponseDto.of(roomId);
	}

	@Override
	public void enterRoom(String roomId, String userId) {
		roomCachePort.enterRoom(roomId, userId);
		roomPersistencePort.addParticipant(roomId, userId);
		//TODO 1.밀린 메시지 가져오기 2.notification 보내기
	}

	@Override
	public void exit(String roomId, String userId) {
		roomCachePort.exit(roomId, userId);
		roomPersistencePort.exitRoom(roomId, userId);
	}

}
