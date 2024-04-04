package com.kor.syh;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kor.syh.filter.AuthorizationFilter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class FilterConfig {

	private final AuthorizationFilter authorizationFilter;

	@Bean
	public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
		return builder.routes()
					  // Login 및 Register
					  .route(r -> r.path("/member/login", "/member/register")
								   .uri("http://localhost:8081"))
					  .route(r -> r.path("/ws/**")
								   .uri("ws://localhost:8082"))
					  // 다른 Member 서비스 경로에 커스텀 필터 적용
					  .route(r -> r.path("/member/**")
								   .filters(f -> f.filter(
									   authorizationFilter.apply(new AuthorizationFilter.Config())))
								   .uri("http://localhost:8081"))
					  // Chat-service
					  .route(r -> r.path("/chat/**", "/room/**")
								   .filters(f -> f.filter(
									   authorizationFilter.apply(new AuthorizationFilter.Config())))
								   .uri("http://localhost:8082"))
					  // Notification-service
					  .route(r -> r.path("/notification/**")
								   .filters(f -> f.filter(
									   authorizationFilter.apply(new AuthorizationFilter.Config())))
								   .uri("http://localhost:8083"))
					  .build();
	}

}
