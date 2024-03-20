package com.kor.syh.members.application.in.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kor.syh.common.CommonResponse;
import com.kor.syh.members.port.in.member.FindMemberResponse;
import com.kor.syh.members.port.in.member.FindMemberUseCase;
import com.kor.syh.members.port.in.member.RegisterMemberCommand;
import com.kor.syh.members.port.in.member.RegisterMemberUseCase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/member")
@RestController
public class MemberController {

	private final RegisterMemberUseCase registerMemberUseCase;
	private final FindMemberUseCase findMemberUseCase;

	@PostMapping("/register")
	public CommonResponse<?> register(@RequestBody RegisterMemberRequest request) {
		RegisterMemberCommand command = new RegisterMemberCommand(
			request.getLoginId(),
			request.getPassword(),
			request.getUsername(),
			request.getNickname()
		);
		registerMemberUseCase.register(command);

		return CommonResponse.success("가입 성공");
	}

	@PostMapping("/find")
	public CommonResponse<FindMemberResponse> find(@Valid @RequestBody FindMemberRequest request) {
		FindMemberResponse findMemberResponse = findMemberUseCase.find(request.getLoginId(), request.getPassword());

		return CommonResponse.of("success", findMemberResponse, null);
	}
}
