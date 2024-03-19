package com.kor.syh.members.domain;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
