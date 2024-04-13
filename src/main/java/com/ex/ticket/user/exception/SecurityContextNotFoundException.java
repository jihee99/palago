package com.ex.ticket.user.exception;

import com.ex.ticket.common.PalagoException;

public class SecurityContextNotFoundException extends PalagoException {

    public static final PalagoException EXCEPTION = new SecurityContextNotFoundException();

    private SecurityContextNotFoundException() { super(UserErrorCode.SECURITY_CONTEXT_NOT_FOUND);}
}
