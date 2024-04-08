package com.kor.syh.config;

import java.util.List;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.kor.syh.common.jwt.TokenException;
import com.kor.syh.common.jwt.JwtUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class MessageInterceptor implements ChannelInterceptor {

	private final JwtUtils jwtUtils;

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
		StompCommand command = accessor.getCommand();

		switch (command) {
			case CONNECT -> {
				String token = extractToken(accessor);
				String userId = setupUserAuthentication(token, accessor);
				log.info("유저 ID '{}' CONNECT", userId);
			}
			case SUBSCRIBE -> {
				String roomId = getRoomId(accessor.getDestination());
				String userId = accessor.getUser().getName();
				UserPrincipalToken user = (UserPrincipalToken)accessor.getUser();
				user.setRoomId(roomId);

				log.info("유저 ID '{}'가 방 '{}'에 입장", userId, roomId);
			}
			case DISCONNECT -> {
				String userId = accessor.getUser().getName();
				UserPrincipalToken user = (UserPrincipalToken)accessor.getUser();
				log.info("유저 ID '{}'가 방 '{}'에 퇴장", userId, user.getRoomId());
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
		if (!jwtUtils.isValidToken(token)) {
			throw new TokenException("토큰 예외");
		}
		return token;
	}

	private String setupUserAuthentication(String token, StompHeaderAccessor accessor) {
		String userId = jwtUtils.parseMemberIdFromToken(token);
		UserPrincipalToken userPrincipalToken = new UserPrincipalToken(userId, null,
			List.of(new SimpleGrantedAuthority("ROLE_USER")));
		accessor.setUser(userPrincipalToken);
		return userId;
	}

	private String getRoomId(String destination) {
		int lastIndex = destination.lastIndexOf('/');
		return lastIndex != -1 ? destination.substring(lastIndex + 1) : null;
	}

}
