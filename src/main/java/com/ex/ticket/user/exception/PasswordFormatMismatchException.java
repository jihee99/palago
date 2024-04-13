package com.ex.ticket.user.exception;

import com.ex.ticket.common.PalagoException;

public class PasswordFormatMismatchException extends PalagoException {

    public static final PalagoException EXCEPTION = new PasswordFormatMismatchException();

    public PasswordFormatMismatchException() {
        super(UserErrorCode.PASSWORD_FORMAT_MISMATCH);
    }
}
