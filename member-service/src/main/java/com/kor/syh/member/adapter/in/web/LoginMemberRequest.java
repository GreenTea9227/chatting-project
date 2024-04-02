package com.kor.syh.member.adapter.in.web;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class LoginMemberRequest {
	private String loginId;
	private String password;
}
