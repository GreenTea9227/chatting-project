package com.kor.syh.member.adapter.out.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.kor.syh.common.CommonResponse;
import com.kor.syh.member.adapter.out.exception.MemberNotFoundException;

@RestControllerAdvice
public class MemberControllerAdvice {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MemberNotFoundException.class)
	public CommonResponse<?> handleMemberNotFoundException(MemberNotFoundException e) {
		return CommonResponse.fail("존재하지 않는 회원입니다.");
	}
}
