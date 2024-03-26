package com.kor.syh.members.adapter.out.exception;

public class PasswordMisMatchException extends RuntimeException {
	public PasswordMisMatchException() {
		super();
	}

	public PasswordMisMatchException(String message) {
		super(message);
	}

	public PasswordMisMatchException(String message, Throwable cause) {
		super(message, cause);
	}

	public PasswordMisMatchException(Throwable cause) {
		super(cause);
	}
}
