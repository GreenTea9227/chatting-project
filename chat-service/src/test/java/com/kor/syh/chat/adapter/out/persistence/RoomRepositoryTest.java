package com.kor.syh.chat.adapter.out.persistence;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.kor.syh.testsupport.IntegrationTestEnvironment;
import com.kor.syh.chat.domain.Room;
import com.kor.syh.common.UnitTest;

@UnitTest
class RoomRepositoryTest extends IntegrationTestEnvironment {

	@Autowired
	private RoomRepository roomRepository;
	@Autowired
	private SpringMongoRoomRepository springMongoRoomRepository;

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

	@Test
	void deleteRoom() {
		//given
		RoomDocument roomDocument = springMongoRoomRepository.save(RoomDocument.builder().build());

		//when
		roomRepository.deleteRoom(roomDocument.getRoomId());

		//then
		assertThat(springMongoRoomRepository.existsById(roomDocument.getRoomId())).isFalse();
	}
}