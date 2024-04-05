package com.kor.syh.filter;

import java.nio.charset.StandardCharsets;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

import com.kor.syh.common.CommonResponse;
import com.kor.syh.common.jwt.TokenException;
import com.kor.syh.common.jwt.JwtUtils;
import com.kor.syh.common.utils.JsonUtil;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class AuthorizationFilter extends AbstractGatewayFilterFactory<AuthorizationFilter.Config> {

	private final JwtUtils jwtUtils;

	public AuthorizationFilter(JwtUtils jwtUtils) {
		super(Config.class);
		this.jwtUtils = jwtUtils;
	}

	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {
			String authorizationHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
			if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
				String token = authorizationHeader.substring(7);
				try {
					if (jwtUtils.isValidToken(token)) {
						String userId = jwtUtils.parseMemberIdFromToken(token);
						exchange.getRequest().mutate()
								.header("X-Authorization-Id", userId)
								.build();
						return chain.filter(exchange); // Token is valid, continue to the next filter
					}
				} catch (TokenException e) {
					log.error("Token validation error: {}", e.getMessage());
				}
			}
			return unauthorizedResponse(exchange); // Token is not valid, respond with unauthorized
		};
	}

	// 인증 실패 Response
	private Mono<Void> unauthorizedResponse(ServerWebExchange exchange) {
		exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
		exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
		DataBufferFactory dataBufferFactory = exchange.getResponse().bufferFactory();
		String responseStr = JsonUtil.classToString(CommonResponse.fail("권한 없음"));
		DataBuffer dataBuffer = dataBufferFactory.wrap(responseStr.getBytes(StandardCharsets.UTF_8));
		return exchange.getResponse().writeWith(Mono.just(dataBuffer));
	}

	public static class Config {
	}

}
