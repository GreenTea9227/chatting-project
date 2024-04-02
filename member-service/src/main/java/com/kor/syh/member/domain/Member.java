package com.kor.syh.member.domain;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class Member {
	private String id;
	private String loginId;
	private String password;
	private String username;
	private String nickname;
	private MemberStatus status;
	private LocalDateTime createdDate;
	private LocalDateTime updateDate;

}
