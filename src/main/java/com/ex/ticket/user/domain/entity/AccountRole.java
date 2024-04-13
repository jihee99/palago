package com.ex.ticket.user.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AccountRole {

	USER("USER"),
	MANAGER("MANAGER"),
	MASTER("MASTER"),
	ADMIN("ADMIN");

	private String value;
}
