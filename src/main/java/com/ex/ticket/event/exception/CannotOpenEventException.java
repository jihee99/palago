package com.ex.ticket.event.exception;

import com.ex.ticket.common.domain.PalagoException;

public class CannotOpenEventException extends PalagoException {

    public static final PalagoException EXCEPTION = new CannotOpenEventException();

    private CannotOpenEventException() {
        super(EventErrorCode.CANNOT_OPEN_EVENT);
    }
}
