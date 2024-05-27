package com.ex.ticket.user.service;

import com.ex.ticket.auth.model.dto.TokenAndUserResponse;
import com.ex.ticket.security.jwt.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshUseCase {

    private final CommonUserService commonUserService;
    private final TokenService tokenService;
//    private final UserDomainService userDomainService;
//    private final RefreshTokenAdaptor refreshTokenAdaptor;
//    private final TokenGenerateHelper tokenGenerateHelper;

    public TokenAndUserResponse execute(String refreshToken) {
//        RefreshTokenEntity savedRefreshTokenEntity = refreshTokenAdaptor.queryRefreshToken(refreshToken);
//
//        Long refreshUserId = tokenService.parseRefreshToken(savedRefreshTokenEntity.getRefreshToken());
//
//        User user = commonUserService.queryUser(refreshUserId);
//        // 리프레쉬 시에도 last로그인 정보 업데이트
//        userDomainService.loginUser(user.getOauthInfo());
//        return tokenGenerateHelper.execute(user);
        return null;
    }
}