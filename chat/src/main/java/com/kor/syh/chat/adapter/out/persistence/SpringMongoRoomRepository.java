package com.kor.syh.chat.adapter.out.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

public interface SpringMongoRoomRepository extends MongoRepository<MongoRoom, String> {

	@Query("{'roomId' :  ?0}")
	@Update("{'$push' : {'messages' :  ?1}}")
	void addMessage(String roomId, MongoMessage message);

}
