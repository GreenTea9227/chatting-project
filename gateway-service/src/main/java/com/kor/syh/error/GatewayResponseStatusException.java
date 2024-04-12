package com.kor.syh.error;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class GatewayResponseStatusException extends RuntimeException {
	private final HttpStatus status;

	public GatewayResponseStatusException(HttpStatus status, String message) {
		super(message);
		this.status = status;
	}

}
