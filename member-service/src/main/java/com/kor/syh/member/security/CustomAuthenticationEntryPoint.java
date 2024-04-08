package com.kor.syh.member.security;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kor.syh.common.CommonResponse;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private final ObjectMapper mapper;

	@Override
	public void commence(HttpServletRequest request,
		HttpServletResponse response,
		AuthenticationException authException) throws IOException, ServletException {

		log.error("Not Authenticated Request", authException);
		CommonResponse<?> commonResponse;

		switch (response.getStatus()) {
			case 404 -> {
				commonResponse = CommonResponse.fail("page not found");
			}
			default -> {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				commonResponse = CommonResponse.fail("인증 되지 않은 사용자입니다.");
			}
		}

		String responseBody = mapper.writeValueAsString(commonResponse);

		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(responseBody);
	}
}
