package com.kor.syh.member.adapter.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "token")
@Entity
public class TokenEntity extends BaseEntity {

	@Id
	private String id;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String accessToken;

	@ManyToOne(fetch = FetchType.LAZY)
	private MemberJpaEntity member;

	// private String userDevice;
	private boolean isValid = true;

	public TokenEntity(String id, String accessToken, MemberJpaEntity member) {
		this.id = id;
		this.accessToken = accessToken;
		this.member = member;
	}
}
