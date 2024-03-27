package com.kor.syh.members.adapter.out.persistence;

import org.springframework.stereotype.Repository;

import com.github.f4b6a3.tsid.TsidCreator;
import com.kor.syh.members.adapter.out.exception.MemberNotFoundException;
import com.kor.syh.members.application.port.out.member.TokenStoragePort;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class TokenRepository implements TokenStoragePort {

	private final SpringJpaTokenRepository tokenRepository;
	private final SpringJpaMemberRepository memberRepository;

	@Override
	public void saveToken(String userId, String token) {
		MemberJpaEntity member = memberRepository.findById(userId)
												 .orElseThrow(() -> new MemberNotFoundException("Member not found"));

		String tokenId = TsidCreator.getTsid().toString();
		TokenEntity tokenEntity = new TokenEntity(tokenId, token, member);
		tokenRepository.save(tokenEntity);
	}

	@Override
	public void deleteToken(String userId) {
		tokenRepository.deleteByMemberId(userId);
	}
}
