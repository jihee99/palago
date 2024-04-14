package com.ex.ticket.security.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.ex.ticket.common.PalagoStatic;

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

}
