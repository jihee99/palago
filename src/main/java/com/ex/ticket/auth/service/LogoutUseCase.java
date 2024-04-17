package com.ex.ticket.auth.service;

import com.ex.ticket.common.util.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutUseCase {

    private final RefreshTokenService refreshTokenService;
    private final UserUtils userUtils;

    public void execute() {
        Long currentUserId = userUtils.getCurrentUserId();
        refreshTokenService.deleteByUserId(currentUserId);
    }
}
