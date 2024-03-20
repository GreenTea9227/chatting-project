package com.kor.syh.members.application.in.web;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class FindMemberRequest {

	@NotBlank
	private String loginId;

	@NotBlank
	private String password;
}
