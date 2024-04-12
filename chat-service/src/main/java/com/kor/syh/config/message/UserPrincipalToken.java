package com.kor.syh.config.message;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;
import lombok.Setter;

public class UserPrincipalToken extends UsernamePasswordAuthenticationToken {

	@Setter
	@Getter
	private String roomId;

	public UserPrincipalToken(Object principal, Object credentials,
		Collection<? extends GrantedAuthority> authorities) {
		super(principal, credentials, authorities);
	}



}
