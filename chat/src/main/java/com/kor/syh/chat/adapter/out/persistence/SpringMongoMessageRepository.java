package com.kor.syh.chat.adapter.out.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpringMongoMessageRepository extends MongoRepository<MongoMessage,String> {
}
