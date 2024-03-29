package com.kor.syh.config;

import java.util.List;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.kor.syh.common.jwt.TokenException;
import com.kor.syh.common.jwt.TokenProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class MessageInterceptor implements ChannelInterceptor {

	private final TokenProvider tokenProvider;

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
		StompCommand command = accessor.getCommand();

		switch (command) {
			case CONNECT -> {
				// 입장 메시지 전송
				String token = extractToken(accessor);
				String userId = tokenProvider.parseMemberIdFromToken(token);
				UsernamePasswordAuthenticationToken authentication =
					new UsernamePasswordAuthenticationToken(userId, null,
						List.of(new SimpleGrantedAuthority("ROLE_USER")));

				accessor.setUser(authentication);
			}
			case SUBSCRIBE -> {
				String roomId = getRoomId(accessor.getDestination());
				String userId = accessor.getUser().getName();
				log.info("유저 ID '{}'가 방 '{}'에 입장", userId, roomId);
			}
			case DISCONNECT -> {
				String userId = accessor.getUser().getName();
				log.info("유저 ID '{}' 퇴장", userId);
			}
		}
		return message;
	}

	private String extractToken(StompHeaderAccessor accessor) {
		String authorizationHeader = String.valueOf(accessor.getNativeHeader("Authorization"));
		if (authorizationHeader == null || authorizationHeader.equals("null")) {
			throw new MessageDeliveryException("메세지 예외");
		}
		String token = authorizationHeader.substring(7);
		if (!tokenProvider.isValidToken(token)) {
			throw new TokenException("토큰 예외");
		}
		return token;
	}

	private String getRoomId(String destination) {
		int lastIndex = destination.lastIndexOf('/');
		return lastIndex != -1 ? destination.substring(lastIndex + 1) : null;
	}

}
