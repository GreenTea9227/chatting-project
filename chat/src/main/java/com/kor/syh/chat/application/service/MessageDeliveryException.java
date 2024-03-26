package com.kor.syh.chat.application.service;

public class MessageDeliveryException extends RuntimeException  {
	public MessageDeliveryException() {
		super();
	}

	public MessageDeliveryException(String message) {
		super(message);
	}

	public MessageDeliveryException(String message, Throwable cause) {
		super(message, cause);
	}

	public MessageDeliveryException(Throwable cause) {
		super(cause);
	}
}
