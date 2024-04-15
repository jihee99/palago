package com.ex.ticket.user.exception;

import com.ex.ticket.common.domain.PalagoException;

public class PasswordIncorrectException extends PalagoException {

    public static final PalagoException EXCEPTION = new PasswordFormatMismatchException();

    private PasswordIncorrectException(){
        super(UserErrorCode.PASSWORD_FORMAT_MISMATCH);
    }
}
