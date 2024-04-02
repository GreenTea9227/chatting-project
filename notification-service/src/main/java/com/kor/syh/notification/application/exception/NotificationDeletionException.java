package com.kor.syh.notification.application.exception;

public class NotificationDeletionException extends RuntimeException {
	public NotificationDeletionException() {
		super();
	}

	public NotificationDeletionException(String message) {
		super(message);
	}

	public NotificationDeletionException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotificationDeletionException(Throwable cause) {
		super(cause);
	}
}
