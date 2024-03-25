package com.kor.syh.common.jwt;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TokenProvider {
	private Key key;
	private final String secretKey;
	private final long tokenValidityInSeconds;
	private final String secretKey2;
	private final long tokenValidityInSeconds2;

	public TokenProvider(
		@Value("${jwt.secret}") String secretKey,
		@Value("${jwt.token_validate_time}") long tokenValidityInSeconds,
		@Value("${jwt.secret2}") String secretKey2,
		@Value("${jwt.token_validate_time2}") long tokenValidityInSeconds2) {
		this.secretKey = secretKey;
		this.tokenValidityInSeconds = tokenValidityInSeconds;
		this.secretKey2 = secretKey2;
		this.tokenValidityInSeconds2 = tokenValidityInSeconds2;
		byte[] secretByteKey = Decoders.BASE64.decode(secretKey);
		this.key = Keys.hmacShaKeyFor(secretByteKey);
	}

	public String generateJwtToken(JwtCreateRequestDto dto) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + tokenValidityInSeconds);

		return Jwts.builder()
				   .setSubject(dto.getId())
				   .setIssuedAt(now)
				   .setExpiration(expiryDate)
				   .signWith(key, SignatureAlgorithm.HS512)
				   .claim("username", dto.getUsername())
				   .compact();
	}

	public String generateRefreshToken(String userId) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + tokenValidityInSeconds2);

		return Jwts.builder()
				   .setSubject(userId)
				   .setIssuedAt(now)
				   .setExpiration(expiryDate)
				   .signWith(key, SignatureAlgorithm.HS512)
				   .compact();
	}

	public String parseMemberIdFromToken(String jwtToken) {
		return Jwts.parserBuilder()
				   .setSigningKey(key)
				   .build()
				   .parseClaimsJws(jwtToken)
				   .getBody()
				   .getSubject();
	}

	public boolean isValidToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			return true;
		} catch (SecurityException | MalformedJwtException e) {
			throw new SecurityException(e.getMessage());
		} catch (ExpiredJwtException e) {
			log.info("유효하지 않은 jwt_token입니다.");
			throw new ExpiredJwtException(e.getHeader(), e.getClaims(), e.getMessage());
		} catch (UnsupportedJwtException e) {
			throw new UnsupportedJwtException(e.getMessage());
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(e);
		}
	}

}
