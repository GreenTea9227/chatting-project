package com.kor.syh.member.adapter.in.web;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class RegisterMemberRequest {

	@NotBlank
	@Length(min = 6, max = 20)
	private String loginId;

	@NotBlank
	@Length(min = 6, max = 20)
	private String password;

	@NotBlank
	private String username;

	@NotBlank
	private String nickname;
}
