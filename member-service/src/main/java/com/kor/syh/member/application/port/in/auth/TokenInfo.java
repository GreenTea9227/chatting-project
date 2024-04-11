package com.kor.syh.member.application.port.in.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenInfo {
	private String accessToken;
	private String refreshToken;
}
