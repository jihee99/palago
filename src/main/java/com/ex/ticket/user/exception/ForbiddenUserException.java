package com.ex.ticket.user.exception;

import com.ex.ticket.common.domain.PalagoException;

public class ForbiddenUserException extends PalagoException {

    public static final PalagoException EXCEPTION = new ForbiddenUserException();

    private ForbiddenUserException() {
        super(UserErrorCode.USER_FORBIDDEN);
    }
}
