package com.kor.syh.chat.adapter.out.persistence;

import org.springframework.stereotype.Repository;

import com.kor.syh.chat.application.port.out.SaveMessagePort;
import com.kor.syh.chat.domain.Message;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class MessageRepository implements SaveMessagePort {

	private final SpringMongoChatRepository springMongoChatRepository;
	private final MessageMapper mapper;

	public void save(Message message) {
		MessageDocument messageDocument = mapper.toEntity(message);
		springMongoChatRepository.save(messageDocument);
	}

}
