package com.kor.syh.member.adapter.out.web;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.kor.syh.common.CommonResponse;
import com.kor.syh.member.adapter.out.exception.MemberNotFoundException;

import javassist.bytecode.DuplicateMemberException;

@RestControllerAdvice
public class MemberControllerAdvice {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({MemberNotFoundException.class, DuplicateMemberException.class})
	public CommonResponse<?> handleMemberNotFoundException(MemberNotFoundException e) {
		return CommonResponse.fail(e.getMessage());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(Exception.class)
	public CommonResponse<?> handleMemberNotFoundException(Exception e) {
		return CommonResponse.fail(e.getMessage());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BindException.class)
	public CommonResponse<?> handleMemberNotFoundException(BindException e) {
		return CommonResponse.failBinding(e);
	}


}
