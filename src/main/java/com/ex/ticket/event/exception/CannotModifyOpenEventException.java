package com.ex.ticket.event.exception;

import com.ex.ticket.common.domain.PalagoException;

public class CannotModifyOpenEventException extends PalagoException {

    public static final PalagoException EXCEPTION = new CannotModifyOpenEventException();
    public CannotModifyOpenEventException() {
        super(EventErrorCode.CANNOT_MODIFY_OPEN_EVENT);
    }
}
