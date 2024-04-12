package com.kor.syh.member.application.port.in.member;

import com.kor.syh.member.adapter.in.web.RegisterMemberRequest;

public interface RegisterMemberUseCase {
	void register(RegisterMemberRequest request);
}
