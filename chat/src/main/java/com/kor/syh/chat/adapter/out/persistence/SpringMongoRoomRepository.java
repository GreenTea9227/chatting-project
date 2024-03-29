package com.kor.syh.chat.adapter.out.persistence;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpringMongoRoomRepository extends MongoRepository<RoomDocument, String> {
	Optional<RoomDocument> findByRoomId(String roomId);

}
