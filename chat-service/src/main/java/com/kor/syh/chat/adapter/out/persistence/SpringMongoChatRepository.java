package com.kor.syh.chat.adapter.out.persistence;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpringMongoChatRepository extends MongoRepository<MessageDocument, String> {

	Optional<MessageDocument> findByRoomId(ObjectId roomId);
}
