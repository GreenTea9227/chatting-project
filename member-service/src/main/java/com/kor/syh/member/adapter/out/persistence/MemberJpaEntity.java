package com.kor.syh.member.adapter.out.persistence;

import com.kor.syh.member.domain.MemberStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
@Entity
public class MemberJpaEntity extends BaseEntity {
	@Id
	private String id;
	private String loginId;
	private String password;
	private String username;
	private String nickname;
	private MemberStatus status;

	public MemberJpaEntity(String id, String loginId, String password, String username, String nickname,
		MemberStatus status) {
		this.id = id;
		this.loginId = loginId;
		this.password = password;
		this.username = username;
		this.nickname = nickname;
		this.status = status;
	}
}
