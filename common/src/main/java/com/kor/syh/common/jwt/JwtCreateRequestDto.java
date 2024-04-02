package com.kor.syh.common.jwt;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JwtCreateRequestDto {
	private String id;
	private String username;
}
