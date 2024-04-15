package com.kor.syh.loggingservice.service;

import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class LoggingConsumer {

	@KafkaListener(topics = "${kafka.logging.TOPIC}")
	public void loggingConsume(ConsumerRecord<String, String> record) {

		long timestamp = record.timestamp();
		long messageId = record.offset();
		String key = record.key();

		try {
			Map<String, String> map = new ObjectMapper().readValue(record.value(), new TypeReference<>() {});
			log.info("Info: [timestamp: {} , messageId: {}] Key: {}", timestamp, messageId, key);

			// send logging
		} catch (JsonProcessingException e) {
			log.error("Error processing message. Topic: {}, Partition: {}, Offset: {}, Key: {}", record.topic(), record.partition(), record.offset(), key);
		}

	}

}
