package com.kor.syh.loggingservice.adapter.in;

import static org.mockito.Mockito.*;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import com.kor.syh.common.utils.JsonUtil;
import com.kor.syh.loggingservice.application.port.out.SendLogPort;
import com.kor.syh.loggingservice.domain.LogEvent;

import io.github.resilience4j.retry.Retry;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.BuilderArbitraryIntrospector;

@SpringBootTest
class LoggingConsumerTest {

	@Autowired
	private LoggingConsumer loggingConsumer;

	@SpyBean
	private Retry retry;

	@MockBean
	private SendLogPort sendLogPort;

	@DisplayName("로그를 성공적으로 남기는 경우 한 번만 시도한다.")
	@Test
	void success_send_log() {
		//given
		LogEvent logEvent = createLogEvent();
		System.out.println(logEvent);
		String str = JsonUtil.classToString(logEvent);
		ConsumerRecord consumerRecord = new ConsumerRecord("topic", 0, 0, "key", str);

		doNothing().when(sendLogPort).sendLog(any(), anyLong());

		//when
		loggingConsumer.loggingConsume(consumerRecord);

		//then
		verify(sendLogPort, times(1)).sendLog(any(), anyLong());
	}

	@DisplayName("로그를 남기는 도중 예외가 발생하면 3번의 재시도를 한다.")
	@Test
	void retry_when_fail_send_log() {
		//given
		LogEvent logEvent = createLogEvent();
		String str = JsonUtil.classToString(logEvent);
		ConsumerRecord consumerRecord = new ConsumerRecord("topic", 0, 0, "key", str);

		doThrow(RuntimeException.class).when(sendLogPort).sendLog(any(), anyLong());

		//when
		Assertions.assertThatThrownBy(() -> loggingConsumer.loggingConsume(consumerRecord))
				  .isInstanceOf(RuntimeException.class);

		//then
		verify(sendLogPort, times(3)).sendLog(any(), anyLong());
	}

	private LogEvent createLogEvent() {
		FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
												   .objectIntrospector(BuilderArbitraryIntrospector.INSTANCE)
												   .build();

		return fixtureMonkey.giveMeOne(LogEvent.class);
	}
}