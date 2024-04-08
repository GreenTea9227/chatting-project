package com.kor.syh.member.adapter.in.web;

import org.hibernate.validator.constraints.Length;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class LoginMemberRequest {

	@Length(min = 6, max = 20)
	private String loginId;

	@Length(min = 6, max = 20)
	private String password;
}
