package com.kor.syh.chat.adapter.out.persistence;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import com.kor.syh.chat.domain.Message;

@Component
public class MessageMapper {

	public MessageDocument toEntity( Message message) {

		return MessageDocument.builder()
							  .roomId(message.getRoomId())
							  .senderId(message.getSenderId())
							  .content(message.getContent())
							  .type(message.getType())
							  .build();
	}

	public Message toDomain(MessageDocument messageDocument) {
		return Message.builder()
					  .messageId(messageDocument.getMessageId().toString())
					  .roomId(messageDocument.getRoomId())
					  .senderId(messageDocument.getSenderId())
					  .content(messageDocument.getContent())
					  .type(messageDocument.getType())
					  .createdDate(messageDocument.getCreateDate())
					  .build();
	}
}
