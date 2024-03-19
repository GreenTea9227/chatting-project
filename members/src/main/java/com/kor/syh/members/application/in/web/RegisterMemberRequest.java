package com.kor.syh.members.application.in.web;

import com.kor.syh.members.domain.MemberStatus;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class RegisterMemberRequest {

	private String loginId;
	private String password;
	private String username;
	private String nickname;
}
