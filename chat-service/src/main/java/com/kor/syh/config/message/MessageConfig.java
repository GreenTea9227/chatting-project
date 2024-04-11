package com.kor.syh.config.message;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSocketMessageBroker
@Configuration
public class MessageConfig implements WebSocketMessageBrokerConfigurer {

	private final MessageInterceptor messageInterceptor;
	private final StompErrorHandler stompErrorHandler;

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.setErrorHandler(stompErrorHandler)
				.addEndpoint("/ws")
				.setAllowedOriginPatterns("*")
				.withSockJS();
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.enableSimpleBroker("/single", "/multiple");
		registry.setApplicationDestinationPrefixes("/send");
		registry.setUserDestinationPrefix("/user");

	}

	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		registration.interceptors(messageInterceptor);
	}

}
