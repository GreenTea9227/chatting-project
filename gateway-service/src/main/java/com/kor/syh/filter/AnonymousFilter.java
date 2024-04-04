package com.kor.syh.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

import com.kor.syh.common.jwt.TokenProvider;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class AnonymousFilter extends AbstractGatewayFilterFactory<AnonymousFilter.Config> {

	private final TokenProvider tokenProvider;

	public static class Config {
	}

	public AnonymousFilter(Class<Config> configClass, TokenProvider tokenProvider) {
		super(configClass);
		this.tokenProvider = tokenProvider;
	}

	@Override
	public GatewayFilter apply(Config config) {

		return ((exchange, chain) -> {
			log.info("AnonymousFilter");
			ServerHttpRequest request = exchange.getRequest();
			String authorizationHeader = request.getHeaders().getFirst("Authorization");
			if (isAnonymousUser(authorizationHeader)) {
				return onError(exchange, "Already Authenticated User", HttpStatus.FORBIDDEN);
			}
			log.info("AnonymousFilter 통과 : {}", request.getRemoteAddress());
			return chain.filter(exchange);
		});
	}

	private boolean isAnonymousUser(String authorizationHeader) {
		return StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")
			&& tokenProvider.isValidToken(authorizationHeader.substring(7));
	}

	private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(httpStatus);
		log.error(err);
		return response.setComplete();
	}

}
