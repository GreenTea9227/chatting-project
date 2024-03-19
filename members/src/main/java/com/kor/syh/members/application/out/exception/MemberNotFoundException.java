package com.kor.syh.members.application.out.exception;

public class MemberNotFoundException extends RuntimeException {
	public MemberNotFoundException() {
		super();
	}

	public MemberNotFoundException(String message) {
		super(message);
	}

	public MemberNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public MemberNotFoundException(Throwable cause) {
		super(cause);
	}
}
