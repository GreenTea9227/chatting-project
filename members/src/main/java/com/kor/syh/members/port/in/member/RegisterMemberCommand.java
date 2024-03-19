package com.kor.syh.members.port.in.member;

import java.time.LocalDateTime;

import com.kor.syh.members.domain.MemberStatus;

public class RegisterMemberCommand {
	private String loginId;
	private String password;
	private String username;
	private String nickname;
	private MemberStatus status;
	private LocalDateTime createdDate;
}
