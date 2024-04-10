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
import com.kor.syh.chat.application.port.out.ManageRoomParticipantPort;
import com.kor.syh.chat.application.port.out.ManageRoomPort;
import com.kor.syh.chat.domain.Room;
import com.kor.syh.common.UnitTest;

@UnitTest
@ExtendWith(MockitoExtension.class)
class RoomServiceTest {

	@InjectMocks
	private RoomService roomService;

	@Mock
	private ManageRoomPort manageRoomPort;
	@Mock
	private ManageRoomParticipantPort manageRoomParticipantPort;

	@DisplayName("success save room")
	@Test
	void success_save_room() {

		// given
		String userId = UUID.randomUUID().toString();
		doNothing().when(manageRoomPort).saveRoom(any(Room.class));
		doNothing().when(manageRoomParticipantPort).createRoom(any(String.class), eq(userId));

		// when
		RoomResponseDto roomResponseDto = roomService.createRoom(userId);

		// then

		verify(manageRoomPort, times(1)).saveRoom(any(Room.class));
		verify(manageRoomParticipantPort, times(1)).createRoom(any(String.class), eq(userId));
		assertThat(roomResponseDto.getRoomId()).isNotBlank();

	}

}