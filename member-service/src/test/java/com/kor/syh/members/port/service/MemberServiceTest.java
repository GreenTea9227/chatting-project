package com.kor.syh.members.port.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.kor.syh.common.UnitTest;
import com.kor.syh.member.adapter.in.web.RegisterMemberRequest;
import com.kor.syh.member.adapter.out.exception.MemberNotFoundException;
import com.kor.syh.member.application.port.in.member.FindMemberResponse;
import com.kor.syh.member.application.port.out.member.FindMemberPort;
import com.kor.syh.member.application.port.out.member.RegisterMemberPort;
import com.kor.syh.member.application.service.MemberService;
import com.kor.syh.member.domain.Member;
import com.kor.syh.member.domain.MemberStatus;

@UnitTest
@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

	@Mock
	private RegisterMemberPort registerMemberPort;
	@Mock
	private FindMemberPort findMemberPort;
	@Mock
	private PasswordEncoder passwordEncoder;

	@InjectMocks
	private MemberService memberService;

	@DisplayName("정상 데이터는 회원 가입에 성공한다.")
	@Test
	void success_register_member() {
		// given
		RegisterMemberRequest command =
			new RegisterMemberRequest("loginId", "password", "username", "nickname");

		doNothing().when(registerMemberPort).register(any(Member.class));
		when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

		// when
		memberService.register(command);

		// then
		verify(registerMemberPort, times(1)).register(any(Member.class));
	}

	@Nested
	class FindMember {
		@DisplayName("이미 등록된 회원은 데이터를 찾아 올 수 있다.")
		@Test
		void success_find_member() {
			// given
			String loginId = "loginId";
			String password = "password";
			String username = "username";
			String nickname = "nickname";
			String id = "id";

			Member member = new Member(id,
				loginId, password,
				username,
				nickname,
				MemberStatus.USER,
				LocalDateTime.now(),
				LocalDateTime.now()
			);

			when(findMemberPort.findByLoginId(loginId)).thenReturn(member);
			when(passwordEncoder.matches(password, member.getPassword())).thenReturn(true);

			// when
			FindMemberResponse findMemberResponse = memberService.find(loginId, password);

			// then
			assertThat(findMemberResponse)
				.extracting(FindMemberResponse::getId,
					FindMemberResponse::getLoginId,
					FindMemberResponse::getUsername,
					FindMemberResponse::getNickname,
					FindMemberResponse::getStatus)
				.containsExactly(id, loginId, username, nickname, MemberStatus.USER);

		}

		@DisplayName("등록되지 않은 회원은 조회가 되지 않는다.")
		@Test
		void fail_find_member_when_no_data() {
			// given
			String loginId = "loginId";
			String password = "password";
			doThrow(new MemberNotFoundException()).when(findMemberPort).findByLoginId(anyString());

			// when , then
			assertThatThrownBy(() -> memberService.find(loginId, password))
				.isInstanceOf(MemberNotFoundException.class);

		}
	}

}