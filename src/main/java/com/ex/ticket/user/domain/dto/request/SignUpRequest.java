package com.ex.ticket.user.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {

	@Schema(description = "아이디", example = "test@test.com")
	private String email;

	@Schema(description = "비밀번호", example = "1234")
	private String password;


	@Schema(description = "이름", example = "홍길동")
	private String name;

}
