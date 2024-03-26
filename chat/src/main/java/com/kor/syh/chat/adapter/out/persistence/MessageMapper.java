package com.kor.syh.chat.adapter.out.persistence;

import org.springframework.stereotype.Component;

import com.kor.syh.chat.domain.Message;

@Component
public class MessageMapper {

	public MongoMessage toMongoMessage(Message message) {
		return MongoMessage.builder()
						   .messageId(message.getMessageId())
						   .senderId(message.getSenderId())
						   .content(message.getContent())
						   .type(message.getType())
						   .createdDate(message.getCreatedDate())
						   .build();
	}

	public Message toMessage(MongoMessage mongoMessage) {

		return Message.builder()
					  .messageId(mongoMessage.getMessageId())
					  .senderId(mongoMessage.getSenderId())
					  .content(mongoMessage.getContent())
					  .type(mongoMessage.getType())
					  .createdDate(mongoMessage.getCreatedDate())
					  .build();
	}
}
