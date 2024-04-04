package com.kor.syh.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.Config> {

	public LoggingFilter() {
		super(Config.class);
	}

	public static class Config {
	}

	@Override
	public GatewayFilter apply(Config config) {
		return new OrderedGatewayFilter((exchange, chain) -> {
			ServerHttpRequest request = exchange.getRequest();
			ServerHttpResponse response = exchange.getResponse();

			log.info("Logging Pre Filter: request id -> [{}] : ip -> [{}] ", request.getId(),
				request.getRemoteAddress());

			return chain.filter(exchange).then(Mono.fromRunnable(() -> {

				log.info("Logging Post Filter: response code -> [{}] : ip -> [{}]  ", response.getStatusCode(),
					request.getRemoteAddress());
			}));
		}, Ordered.LOWEST_PRECEDENCE);
	}

}
