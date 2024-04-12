package com.kor.syh.member.adapter.out.exception;

public class DuplicateLoginIdException extends RuntimeException{
	public DuplicateLoginIdException() {
		super();
	}

	public DuplicateLoginIdException(Throwable cause) {
		super(cause);
	}

	public DuplicateLoginIdException(String message) {
		super(message);
	}

	public DuplicateLoginIdException(String message, Throwable cause) {
		super(message, cause);
	}
}
