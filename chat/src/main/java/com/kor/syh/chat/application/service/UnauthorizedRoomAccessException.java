package com.kor.syh.chat.application.service;

public class UnauthorizedRoomAccessException extends RuntimeException {
	public UnauthorizedRoomAccessException() {
		super();
	}

	public UnauthorizedRoomAccessException(String message) {
		super(message);
	}

	public UnauthorizedRoomAccessException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnauthorizedRoomAccessException(Throwable cause) {
		super(cause);
	}
}
