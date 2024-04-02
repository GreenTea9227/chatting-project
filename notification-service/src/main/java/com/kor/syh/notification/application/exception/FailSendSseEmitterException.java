package com.kor.syh.notification.application.exception;

import java.io.IOException;

public class FailSendSseEmitterException extends RuntimeException {
	public FailSendSseEmitterException() {
		super();
	}

	public FailSendSseEmitterException(String message) {
		super(message);
	}

	public FailSendSseEmitterException(String message, Throwable cause) {
		super(message, cause);
	}

	public FailSendSseEmitterException(Throwable cause) {
		super(cause);
	}
}
