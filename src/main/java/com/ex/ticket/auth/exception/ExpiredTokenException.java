package com.ex.ticket.auth.exception;

import com.ex.ticket.common.domain.PalagoException;
import com.ex.ticket.user.exception.UserErrorCode;

public class ExpiredTokenException extends PalagoException {
    public static final PalagoException EXCEPTION = new ExpiredTokenException();
    private ExpiredTokenException() {
        super(UserErrorCode.TOKEN_EXPIRED);
    }
}
