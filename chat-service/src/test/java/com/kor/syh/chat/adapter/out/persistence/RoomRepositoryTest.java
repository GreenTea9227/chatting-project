package com.kor.syh.chat.adapter.out.persistence;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;

import com.kor.syh.chat.MongoTestContainers;
import com.kor.syh.chat.domain.Room;

@Import({RoomMapper.class, RoomRepository.class})
@DataMongoTest
class RoomRepositoryTest extends MongoTestContainers {
	@Autowired
	private RoomRepository roomRepository;
	@Autowired
	private SpringMongoRoomRepository springMongoRoomRepository;

	@BeforeEach
	void setUp() {
		springMongoRoomRepository.deleteAll();
	}

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