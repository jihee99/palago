package com.ex.ticket.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@ConfigurationProperties(prefix = "auth.jwt")
public class JwtProperties {
	private String secretKey;
}
