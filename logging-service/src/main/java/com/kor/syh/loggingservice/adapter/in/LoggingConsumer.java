package com.kor.syh.loggingservice.adapter.in;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.kor.syh.common.utils.JsonUtil;
import com.kor.syh.loggingservice.application.port.out.SendLogPort;
import com.kor.syh.loggingservice.domain.LogEvent;

import io.github.resilience4j.retry.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class LoggingConsumer {

	private final SendLogPort sendLogPort;
	private final Retry retry;

	@KafkaListener(topics = "${kafka.logging.TOPIC}")
	public void loggingConsume(ConsumerRecord<String, String> record) {

		LogEvent logEvent = JsonUtil.stringToClass(record.value(), LogEvent.class);
		Retry.decorateRunnable(retry, () -> {
			sendLogPort.sendLog(logEvent, record.timestamp());
		}).run();

	}

}
