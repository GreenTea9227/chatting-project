package com.kor.syh.chat.adapter.out.persistence;

import org.springframework.stereotype.Repository;

import com.kor.syh.chat.application.port.out.ManageMessagePort;
import com.kor.syh.chat.domain.Message;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class MessageRepository implements ManageMessagePort {

	private final SpringMongoMessageRepository springMongoMessageRepository;
	private final MessageMapper mapper;
	@Override
	public void save(Message message) {
		MongoMessage mongoMessage = mapper.toMongoMessage(message);
		springMongoMessageRepository.save(mongoMessage);
	}
}
