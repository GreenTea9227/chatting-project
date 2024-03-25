package com.kor.syh.chat.adapter.out.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpringMongoRoomRepository extends MongoRepository<MongoRoom, String> {
}
