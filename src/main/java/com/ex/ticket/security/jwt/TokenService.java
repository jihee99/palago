package com.ex.ticket.security.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.ex.ticket.common.PalagoStatic;
import com.ex.ticket.common.domain.dto.AccessTokenInfo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {

	private final JwtProperties jwtProperties;

	private Key getSecretKey() {
		return Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8));
	}

	private Jws<Claims> getJws(String token) {
		try {
			return Jwts.parserBuilder().setSigningKey(getSecretKey()).build().parseClaimsJws(token);
		} catch (ExpiredJwtException e) {
			throw new RuntimeException("만료된 토큰");
//            throw ExpiredTokenException.EXCEPTION;
		} catch (Exception e) {
			throw new RuntimeException("유효하지 않은 토큰");
//            throw InvalidTokenException.EXCEPTION;
		}
	}


	public String generateAccessToken(String email, String role){
		final Date issuedAt = new Date();
		final Date accessTokenExpiresIn = new Date(issuedAt.getTime() + PalagoStatic.ACCESS_TOKEN_TIME);
		final Key encodedKey = getSecretKey();

		return Jwts.builder()
			.setIssuedAt(issuedAt)
			.setSubject(email)
			.claim(PalagoStatic.TOKEN_TYPE, PalagoStatic.ACCESS_TOKEN)
			.claim(PalagoStatic.TOKEN_ROLE, role)
			.setExpiration(accessTokenExpiresIn)
			.signWith(encodedKey)
			.compact();
	}

	public boolean isAccessToken(String token) {
		return getJws(token).getBody().get(PalagoStatic.TOKEN_TYPE).equals(PalagoStatic.ACCESS_TOKEN);
	}

	public boolean isRefreshToken(String token) {
		return getJws(token).getBody().get(PalagoStatic.TOKEN_TYPE).equals(PalagoStatic.REFRESH_TOKEN);
	}

	public AccessTokenInfo parseAccessToken(String token) {
		if (isAccessToken(token)) {
			Claims claims = getJws(token).getBody();
			return AccessTokenInfo.builder()
				.userId(Long.parseLong(claims.getSubject()))
//				.email(claims.getSubject())
				.role((String) claims.get(PalagoStatic.TOKEN_ROLE))
				.build();
		}
		// throw InvalidTokenException.EXCEPTION;
		return null;
	}
}
