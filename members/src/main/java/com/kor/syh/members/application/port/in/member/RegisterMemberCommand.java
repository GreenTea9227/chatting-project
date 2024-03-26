package com.kor.syh.members.application.port.in.member;

import com.kor.syh.common.SelfValidating;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class RegisterMemberCommand extends SelfValidating<RegisterMemberCommand> {

	@NotBlank
	private String loginId;

	@NotBlank
	private String password;

	@NotBlank
	private String username;

	@NotBlank
	private String nickname;

	public RegisterMemberCommand(String loginId, String password, String username, String nickname) {
		this.loginId = loginId;
		this.password = password;
		this.username = username;
		this.nickname = nickname;
		validateSelf();
	}
}
