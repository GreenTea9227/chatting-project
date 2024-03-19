package com.kor.syh.members.port.in.member;

import com.kor.syh.common.SelfValidating;
import com.kor.syh.members.domain.MemberStatus;

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

	@NotBlank
	private MemberStatus status;

	public RegisterMemberCommand(String loginId, String password, String username, String nickname,
		MemberStatus status) {
		this.loginId = loginId;
		this.password = password;
		this.username = username;
		this.nickname = nickname;
		this.status = status;

		validateSelf();
	}
}
