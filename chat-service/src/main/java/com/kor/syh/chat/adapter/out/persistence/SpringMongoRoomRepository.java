package com.kor.syh.chat.adapter.out.persistence;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

public interface SpringMongoRoomRepository extends MongoRepository<RoomDocument, String> {
	Optional<RoomDocument> findByRoomId(String roomId);

	@Query("{ '_id' :  ?0 }")
	@Update("{'$addToSet' : { participants :  ?1 }}")
	void addParticipant(String roomId, String userId);

	@Query("{ '_id' :  ?0 }")
	@Update("{'$pull' : { participants : ?1 }}")
	void removeParticipant(String roomId, String userId);

	Slice<RoomDocument> findAllBy(Pageable pageable);

}
