package com.kor.syh.chat.adapter.out.persistence;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import com.kor.syh.chat.domain.Room;
import com.kor.syh.common.UnitTest;
import com.kor.syh.testsupport.IntegrationTestEnvironment;

@UnitTest
class RoomRepositoryTest extends IntegrationTestEnvironment {

	@Autowired
	private RoomPersistenceRepository roomRepository;
	@Autowired
	private SpringMongoRoomRepository springMongoRoomRepository;

	@DisplayName("Save Room Test")
	@Test
	void saveRoom() {
		//given
		String roomId = "roomId";
		String creatorId = "creatorId";
		Room room = Room.builder()
						.roomId(roomId)
						.creatorId(creatorId)
						.build();

		//when
		roomRepository.saveRoom(room);

		//then
		RoomDocument roomDocument = springMongoRoomRepository.findByRoomId(roomId).orElseThrow();
		assertThat(roomDocument.getRoomId()).isEqualTo(roomId);
		assertThat(roomDocument.getCreateDate()).isBeforeOrEqualTo(LocalDateTime.now());

	}

	@DisplayName("Delete Room Test")
	@Test
	void deleteRoom() {
		//given
		RoomDocument roomDocument = springMongoRoomRepository.save(RoomDocument.builder().build());

		//when
		roomRepository.deleteRoom(roomDocument.getRoomId());

		//then
		assertThat(springMongoRoomRepository.existsById(roomDocument.getRoomId())).isFalse();
	}

	@DisplayName("Add Participant Test")
	@Test
	void addParticipant() {
		// given
		String roomId = "roomId";
		String firstId = "firstId";
		String secondId = "secondId";
		RoomDocument roomDocument = springMongoRoomRepository.save(RoomDocument.builder()
																			   .roomId(roomId)
																			   .build());

		// when
		roomRepository.addParticipant(roomDocument.getRoomId(), firstId);
		roomRepository.addParticipant(roomDocument.getRoomId(), firstId);
		roomRepository.addParticipant(roomDocument.getRoomId(), secondId);

		// then
		RoomDocument findRoom = springMongoRoomRepository.findById(roomId).orElseThrow();

		assertThat(findRoom.getParticipants()).size().isEqualTo(2);
		assertThat(findRoom.getParticipants()).containsExactly(firstId, secondId);
	}

	@DisplayName("exit room test")
	@Test
	void exitRoom() {
		// given
		String roomId = "roomId";
		String firstId = "firstId";
		String secondId = "secondId";
		String thirdId = "thirdId";
		RoomDocument roomDocument = springMongoRoomRepository.save(RoomDocument.builder()
																			   .roomId(roomId)
																			   .participants(List.of(firstId, secondId,thirdId))
																			   .build());
		// when
		roomRepository.exitRoom(roomId,secondId);

		// then
		RoomDocument findRoom = springMongoRoomRepository.findById(roomId).orElseThrow();

		assertThat(findRoom.getParticipants()).size().isEqualTo(2);
		assertThat(findRoom.getParticipants()).containsExactly(firstId, thirdId);
	}

	@DisplayName("Get room list test")
	@Test
	void get_rooms_by_pageable() {
		// given
		for (int i = 0; i < 5; i++) {
			springMongoRoomRepository.save(RoomDocument.builder().build());
		}

		// when
		PageRequest pageRequest = PageRequest.of(0, 10);
		Slice<RoomDocument> rooms = springMongoRoomRepository.findAllBy(pageRequest);

		// then
		assertThat(rooms.hasNext()).isFalse();
		assertThat(rooms.hasPrevious()).isFalse();
		assertThat(rooms.getContent()).size().isEqualTo(5);
	}


}