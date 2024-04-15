package com.ex.ticket.security.jwt;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccessTokenInfo {
	private final String email;
	private final String role;
}
