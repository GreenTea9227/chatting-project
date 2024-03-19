package com.kor.syh.port.in.member;

import java.time.LocalDateTime;

import com.kor.syh.domain.MemberStatus;

public class RegisterMemberCommand {
	private String loginId;
	private String password;
	private String username;
	private String nickname;
	private MemberStatus status;
	private LocalDateTime createdDate;
}
