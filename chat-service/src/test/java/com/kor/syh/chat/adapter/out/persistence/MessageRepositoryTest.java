package com.kor.syh.chat.adapter.out.persistence;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;

import com.kor.syh.chat.MongoTestContainers;
import com.kor.syh.chat.domain.Message;
import com.kor.syh.chat.domain.MessageType;

@Import({MessageRepository.class, MessageMapper.class})
@DataMongoTest
class MessageRepositoryTest extends MongoTestContainers {

	@Autowired
	private MessageRepository messageRepository;
	@Autowired
	private SpringMongoChatRepository springMongoChatRepository;

	@BeforeEach
	void setUp() {
		springMongoChatRepository.deleteAll();
	}

	@DisplayName("save message")
	@Test
	void save_message() {
		// given
		String roomId = UUID.randomUUID().toString();
		Message message = Message.builder()
								 .type(MessageType.SEND)
								 .content("hello")
								 .senderId("senderId")
								 .roomId(roomId)
								 .build();

		// when
		messageRepository.save(message);

		// then
		MessageDocument messageDocument = springMongoChatRepository.findByRoomId(roomId).orElseThrow();

		assertThat(messageDocument.getMessageId()).isNotNull();
		assertThat(messageDocument.getRoomId()).isEqualTo(roomId);
		assertThat(messageDocument.getCreateDate()).isBefore(LocalDateTime.now());
	}

}