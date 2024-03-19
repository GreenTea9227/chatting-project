package com.kor.syh.application.out.persistence;

import com.kor.syh.domain.MemberStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class MemberJpaEntity extends BaseEntity {
	@Id
	@GeneratedValue
	private String id;
	private String loginId;
	private String password;
	private String username;
	private String nickname;
	private MemberStatus status;

	public MemberJpaEntity(String loginId, String password, String username, String nickname, MemberStatus status) {
		this.loginId = loginId;
		this.password = password;
		this.username = username;
		this.nickname = nickname;
		this.status = status;
	}
}
