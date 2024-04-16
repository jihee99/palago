package com.ex.ticket.user.domain.dto.response;

import com.ex.ticket.user.domain.entity.AccountRole;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignInResponse {

        @Schema(description = "회원 아이디", example = "1")
        private final Long userId;

        @Schema(description = "로그인 아이디", example = "user1@test.com")
        private final String userName;

        @Schema(description = "로그인 아이디", example = "user1@test.com")
        private final String name;

        @Schema(description = "회원 유형", example = "USER")
        private final AccountRole role;

        private final String accessToken;

        private final String refreshToken;
}
