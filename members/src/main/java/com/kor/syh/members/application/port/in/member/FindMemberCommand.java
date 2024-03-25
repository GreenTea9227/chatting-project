package com.kor.syh.members.application.port.in.member;

import com.kor.syh.common.SelfValidating;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class FindMemberCommand extends SelfValidating<FindMemberCommand> {

	@NotBlank
	private String loginId;

	@NotBlank
	private String password;

	public FindMemberCommand(String loginId, String password) {
		this.loginId = loginId;
		this.password = password;
		validateSelf();
	}
}
