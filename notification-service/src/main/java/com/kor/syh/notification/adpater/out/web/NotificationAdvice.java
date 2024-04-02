package com.kor.syh.notification.adpater.out.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.kor.syh.common.CommonResponse;
import com.kor.syh.notification.application.exception.FailSendSseEmitterException;
import com.kor.syh.notification.application.exception.NotFoundSseEmitterException;

@RestControllerAdvice
public class NotificationAdvice {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({NotFoundSseEmitterException.class, FailSendSseEmitterException.class})
	public CommonResponse<?> handleException(NotFoundSseEmitterException e) {
		return CommonResponse.fail(e.getMessage());
	}
}
