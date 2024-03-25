package com.kor.syh.members.application.port.in.member;

import com.kor.syh.members.domain.MemberStatus;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class FindMemberResponse {
	private String id;
	private String loginId;
	private String username;
	private String nickname;
	private MemberStatus status;
}
