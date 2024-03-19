package com.kor.syh.members.application.in.web;

import com.kor.syh.common.SelfValidating;
import com.kor.syh.members.domain.MemberStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class RegisterMemberRequest {

	private String loginId;

	private String password;

	private String username;

	private String nickname;

	private MemberStatus status;

}
