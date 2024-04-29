package com.kor.syh.loggingservice.config;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class CustomRetryConfig {

	@Bean
	public Retry RetryConfigCustomizer() {
		RetryConfig config = RetryConfig.custom()
										.maxAttempts(3)
										.waitDuration(Duration.ofMillis(1000))
										.failAfterMaxAttempts(true)
										.build();

		RetryRegistry registry = RetryRegistry.of(config);
		Retry retry = registry.retry("cloudWatchLogging");
		retry.getEventPublisher()
			 .onRetry(event -> {
				 log.info("Retry attempt #{}, calling: {}", event.getNumberOfRetryAttempts(), event.getName());
			 })
			 .onSuccess(event -> {
				 log.info("Retry successful after {} attempts", event.getNumberOfRetryAttempts());
			 })
			 .onError(event -> {
				 log.error("Retry failed after {} attempts", event.getNumberOfRetryAttempts(),
					 event.getLastThrowable());
			 });

		return retry;
	}
}
