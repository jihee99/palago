package com.ex.ticket.user.exception;

import com.ex.ticket.common.domain.PalagoException;

public class UserNotFoundException extends PalagoException {

    public static final PalagoException EXCEPTION = new UserNotFoundException();

    private UserNotFoundException() {
        super(UserErrorCode.USER_NOT_FOUND);
    }
}
