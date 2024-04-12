package com.kor.syh.filter;

import java.nio.charset.StandardCharsets;

import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.kor.syh.common.CommonResponse;
import com.kor.syh.common.utils.JsonUtil;
import com.kor.syh.error.GatewayResponseStatusException;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Order(-1)
@Component
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {
	@Override
	public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {

		String message = ex.getMessage();

		if (ex instanceof GatewayResponseStatusException) {
			exchange.getResponse().setStatusCode(((GatewayResponseStatusException)ex).getStatus());
		} else {
			exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
		}

		exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
		DataBufferFactory dataBufferFactory = exchange.getResponse().bufferFactory();

		String responseStr = JsonUtil.classToString(CommonResponse.fail(message));
		DataBuffer dataBuffer = dataBufferFactory.wrap(responseStr.getBytes(StandardCharsets.UTF_8));

		return exchange.getResponse().writeWith(Mono.just(dataBuffer));

	}

}
