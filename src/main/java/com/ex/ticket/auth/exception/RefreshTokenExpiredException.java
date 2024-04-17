package com.ex.ticket.auth.exception;

import com.ex.ticket.common.domain.PalagoException;
import com.ex.ticket.user.exception.UserErrorCode;

public class RefreshTokenExpiredException extends PalagoException {
    public static final PalagoException EXCEPTION = new RefreshTokenExpiredException();
    private RefreshTokenExpiredException() {
        super(UserErrorCode.REFRESH_TOKEN_EXPIRED);
    }
}
