package com.kor.syh.member.adapter.in.web;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kor.syh.common.CommonResponse;
import com.kor.syh.common.jwt.JwtToken;
import com.kor.syh.member.application.port.in.auth.LoginMemberUseCase;
import com.kor.syh.member.application.port.in.auth.LogoutMemberUseCase;
import com.kor.syh.member.application.port.in.auth.TokenInfo;
import com.kor.syh.member.application.port.in.member.FindMemberResponse;
import com.kor.syh.member.application.port.in.member.FindMemberUseCase;
import com.kor.syh.member.application.port.in.member.RegisterMemberUseCase;
import com.kor.syh.member.utils.HttpRequestUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class MemberController {

	private final RegisterMemberUseCase registerMemberUseCase;
	private final FindMemberUseCase findMemberUseCase;
	private final LoginMemberUseCase loginMemberUseCase;
	private final LogoutMemberUseCase logoutMemberUseCase;

	@PostMapping("/register")
	public ResponseEntity<CommonResponse<?>> register(@Valid @RequestBody RegisterMemberRequest request) {

		registerMemberUseCase.register(request);

		return new ResponseEntity<>(CommonResponse.success("가입 성공"), HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public CommonResponse<?> login(@Valid @RequestBody LoginMemberRequest request,
		HttpServletRequest httpServletRequest) {
		String clientIp = HttpRequestUtils.getClientIp(httpServletRequest);
		TokenInfo tokenInfo = loginMemberUseCase.login(request.getLoginId(), request.getPassword(), clientIp);

		return CommonResponse.success(tokenInfo, "로그인 성공");
	}

	@GetMapping("/logout")
	public CommonResponse<?> logout(Principal principal, HttpServletRequest httpServletRequest) {
		String userId = principal.getName();

		String clientIp = HttpRequestUtils.getClientIp(httpServletRequest);

		logoutMemberUseCase.logout(userId, clientIp);
		return CommonResponse.success("로그아웃 성공");
	}

	@PostMapping("/find")
	public CommonResponse<FindMemberResponse> find(@Valid @RequestBody FindMemberRequest request) {

		FindMemberResponse findMemberResponse = findMemberUseCase.find(request.getLoginId(), request.getPassword());

		return CommonResponse.of("success", findMemberResponse, null);
	}

}
