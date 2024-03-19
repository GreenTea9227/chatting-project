package com.kor.syh.domain;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class Member {
	private String id;
	private String loginId;
	private String password;
	private String username;
	private String nickname;
	private MemberStatus status;
	private LocalDateTime createdDate;

}
