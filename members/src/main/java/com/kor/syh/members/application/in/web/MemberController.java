package com.kor.syh.members.application.in.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kor.syh.common.CommonResponse;
import com.kor.syh.members.port.in.member.RegisterMemberCommand;
import com.kor.syh.members.port.in.member.RegisterMemberUseCase;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/member")
@RestController
public class MemberController {

	private final RegisterMemberUseCase registerMemberUseCase;

	@PostMapping("/register")
	public CommonResponse<?> register(@RequestBody RegisterMemberRequest request) {
		RegisterMemberCommand command = new RegisterMemberCommand(
			request.getLoginId(),
			request.getPassword(),
			request.getUsername(),
			request.getNickname(),
			request.getStatus()
		);
		registerMemberUseCase.register(command);

		return CommonResponse.success("가입 성공");
	}
}
