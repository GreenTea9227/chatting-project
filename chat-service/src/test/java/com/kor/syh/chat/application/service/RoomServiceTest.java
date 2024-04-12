package com.kor.syh.chat.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.kor.syh.chat.adapter.in.web.RoomResponseDto;
import com.kor.syh.chat.application.port.out.RoomCachePort;
import com.kor.syh.chat.application.port.out.RoomPersistencePort;
import com.kor.syh.chat.domain.Room;
import com.kor.syh.common.UnitTest;

@UnitTest
@ExtendWith(MockitoExtension.class)
class RoomServiceTest {

	@InjectMocks
	private RoomService roomService;

	@Mock
	private RoomPersistencePort roomPersistencePort;
	@Mock
	private RoomCachePort roomCachePort;

	@DisplayName("success save room")
	@Test
	void success_save_room() {

		// given
		String userId = UUID.randomUUID().toString();
		doNothing().when(roomPersistencePort).saveRoom(any(Room.class));
		doNothing().when(roomCachePort).createRoom(any(String.class), eq(userId));

		// when
		RoomResponseDto roomResponseDto = roomService.createRoom(userId);

		// then

		verify(roomPersistencePort, times(1)).saveRoom(any(Room.class));
		verify(roomCachePort, times(1)).createRoom(any(String.class), eq(userId));
		assertThat(roomResponseDto.getRoomId()).isNotBlank();

	}

	@DisplayName("success create room")
	@Test
	void success_create_room() {
		// given
		String userId = "userId";
		doNothing().when(roomPersistencePort).saveRoom(any(Room.class));
		doNothing().when(roomCachePort).createRoom(any(String.class),eq(userId));

		// when
		RoomResponseDto room = roomService.createRoom(userId);

		// then
		assertThat(room.getRoomId()).isNotBlank();
		verify(roomPersistencePort, times(1)).saveRoom(any(Room.class));
		verify(roomCachePort, times(1)).createRoom(any(String.class), eq(userId));
	}


}