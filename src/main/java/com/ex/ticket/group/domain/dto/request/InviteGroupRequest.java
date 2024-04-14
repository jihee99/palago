package com.ex.ticket.group.domain.dto.request;

import com.ex.ticket.common.annotation.Enum;
import com.ex.ticket.group.domain.entity.GroupUserRole;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.Getter;

@Getter
public class InviteGroupRequest {
	@Schema(defaultValue = "user@test.com", description = "초대할 유저 이메일 주소")
	@Email(message = "올바른 이메일을 입력해주세요")
	private String email;

	@Schema(defaultValue = "MANAGER", description = "그룹 유저 역할")
	@Enum(message = "MANAGER 또는 GUEST 만 허용됩니다")
	private GroupUserRole role;
}
