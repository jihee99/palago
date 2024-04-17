package com.ex.ticket.auth.service;

import com.ex.ticket.auth.exception.RefreshTokenExpiredException;
import com.ex.ticket.auth.model.RefreshTokenEntity;
import com.ex.ticket.user.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenEntity queryRefreshToken(String refreshToken) {
        return refreshTokenRepository
                .findByRefreshToken(refreshToken)
                .orElseThrow(() ->
                        RefreshTokenExpiredException.EXCEPTION);
    }

    public RefreshTokenEntity save(RefreshTokenEntity refreshToken) {
        return refreshTokenRepository.save(refreshToken);
    }

    public void deleteByUserId(Long userId) {
        refreshTokenRepository.deleteById(userId.toString());
    }
}
