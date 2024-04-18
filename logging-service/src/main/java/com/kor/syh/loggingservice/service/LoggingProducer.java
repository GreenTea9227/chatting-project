package com.kor.syh.loggingservice.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.kor.syh.common.log.LogMessage;
import com.kor.syh.common.utils.JsonUtil;
import com.kor.syh.loggingservice.config.KafkaProperties;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LoggingProducer {

	private final KafkaTemplate<String, Object> kafkaTemplate;
	private final KafkaProperties kafkaProperties;

	public void sendLog(LogMessage logMessage) {
		String message = JsonUtil.classToString(logMessage);
		kafkaTemplate.send(kafkaProperties.TOPIC, message);
	}

}
