package com.ex.ticket.user.exception;

import com.ex.ticket.common.PalagoException;

public class AlreadySignUpUserException extends PalagoException {

    public static final PalagoException EXCEPTION = new AlreadySignUpUserException();

    private AlreadySignUpUserException(){
        super(UserErrorCode.USER_ALREADY_SIGNUP);
    }
}
