package com.kor.syh.notification.application.exception;

public class NotFoundSseEmitterException extends RuntimeException {
	public NotFoundSseEmitterException() {
		super();
	}

	public NotFoundSseEmitterException(String message) {
		super(message);
	}

	public NotFoundSseEmitterException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotFoundSseEmitterException(Throwable cause) {
		super(cause);
	}
}
