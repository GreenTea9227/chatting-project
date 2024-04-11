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
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtUtils {
	private final String secretKey;
	private final long tokenValidityInSeconds;
	private final String secretKey2;
	private final long tokenValidityInSeconds2;
	private Key key;

	public JwtUtils(
		@Value("${jwt.secret}") String secretKey,
		@Value("${jwt.token_validate_time}") long tokenValidityInSeconds,
		@Value("${jwt.secret2}") String secretKey2,
		@Value("${jwt.token_validate_time2}") long tokenValidityInSeconds2) {
		this.secretKey = secretKey;
		this.tokenValidityInSeconds = tokenValidityInSeconds * 1000;
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

	public String generateRefreshToken(JwtCreateRequestDto dto) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + tokenValidityInSeconds2);

		return Jwts.builder()
				   .setSubject(dto.getId())
				   .setIssuedAt(now)
				   .setExpiration(expiryDate)
				   .signWith(key, SignatureAlgorithm.HS512)
				   .claim("username", dto.getUsername())
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
		} catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
			log.error("Invalid JWT Token", e);
		} catch (ExpiredJwtException e) {
			log.error("Expired JWT Token", e);
		} catch (UnsupportedJwtException e) {
			log.error("Unsupported JWT Token", e);
		} catch (IllegalArgumentException e) {
			log.error("JWT claims string is empty.", e);
		}
		return false;
	}
}
