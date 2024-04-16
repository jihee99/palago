package com.ex.ticket.auth.model.dto;

import com.ex.ticket.user.domain.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenAndUserResponse {
    @Schema(description = "어세스 토큰")
    private final String accessToken;

    private final Long accessTokenAge;

    @Schema(description = "리프레쉬 토큰")
    private final String refreshToken;

    private final Long refreshTokenAge;

    private final User user;
}
