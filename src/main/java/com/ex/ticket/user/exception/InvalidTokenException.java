package com.ex.ticket.user.exception;

import com.ex.ticket.common.domain.PalagoException;

public class InvalidTokenException extends PalagoException {
    public static final PalagoException EXCEPTION = new InvalidTokenException();

    private InvalidTokenException() {
        super(UserErrorCode.INVALID_TOKEN);
    }
}
