package com.ex.ticket.user.domain.dto.response;

import com.ex.ticket.user.domain.entity.User;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SignUpResponse {

	@Schema(description = "회원고유아이디", example = "1")
	private Long userId;

	@Schema(description = "회원아이디", example = "test@test.com")
	private String email;

	@Schema(description = "이름", example = "홍길동")
	private String name;

	public static SignUpResponse from(User user) {
		return new SignUpResponse(
			user.getUserId(),
			user.getEmail(),
			user.getName()
		);
	}

}
