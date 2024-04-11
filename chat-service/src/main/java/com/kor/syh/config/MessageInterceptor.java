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

import com.kor.syh.common.jwt.JwtUtils;
import com.kor.syh.common.jwt.TokenException;

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

		if (command == StompCommand.CONNECT) {
			String userId = extractUserIdFromToken(accessor);
			UserPrincipalToken principalToken = createUserAuthentication(userId);
			accessor.setUser(principalToken);
			log.info("유저 ID '{}' CONNECT", userId);
		}
		return message;
	}

	/**
	 * postSend 메서드에서 preSend에서 검증된 사용자 principal 값을 활용합니다.
	 * SUBSCRIBE 명령어인 경우 방에 입장한 사용자의 ID와 방 ID를 로깅하고,
	 * SEND 명령어인 경우 사용자의 ID를 로깅합니다.
	 * DISCONNECT 명령어인 경우 사용자가 퇴장한 방의 ID를 로깅합니다.
	 */
	@Override
	public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
		StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
		StompCommand command = accessor.getCommand();
		switch (command) {
			case SUBSCRIBE -> {
				String roomId = getRoomId(accessor.getDestination());
				String userId = accessor.getUser().getName();
				UserPrincipalToken user = (UserPrincipalToken)accessor.getUser();
				user.setRoomId(roomId);

				log.info("유저 ID '{}'가 방 '{}'에 입장", userId, roomId);
			}
			case SEND -> {
				String userId = accessor.getUser().getName();
				log.info("유저 ID '[{}]' : send message ", userId);
			}
			case DISCONNECT -> {
				String userId = accessor.getUser().getName();
				UserPrincipalToken user = (UserPrincipalToken)accessor.getUser();
				log.info("유저 ID '{}'가 방 '{}'에 퇴장", userId, user.getRoomId());
			}

		}
	}

	private String extractUserIdFromToken(StompHeaderAccessor accessor) {
		String authorizationHeader = String.valueOf(accessor.getNativeHeader("Authorization"));
		if (authorizationHeader == null || authorizationHeader.equals("null")) {
			throw new MessageDeliveryException("메세지 예외");
		}
		String token = authorizationHeader.substring(7);
		if (!jwtUtils.isValidToken(token)) {
			throw new TokenException("토큰 예외");
		}
		return jwtUtils.parseMemberIdFromToken(token);
	}

	private UserPrincipalToken createUserAuthentication(String userId) {
		return new UserPrincipalToken(userId, null, List.of(new SimpleGrantedAuthority("ROLE_USER")));
	}

	private String getRoomId(String destination) {
		int lastIndex = destination.lastIndexOf('/');
		return lastIndex != -1 ? destination.substring(lastIndex + 1) : null;
	}

}
