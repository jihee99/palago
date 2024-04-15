package com.ex.ticket.event.exception;

import com.ex.ticket.common.domain.PalagoException;

public class CannotDeleteByOpenEventException extends PalagoException {

    public static final PalagoException EXCEPTION = new CannotDeleteByOpenEventException();

    private CannotDeleteByOpenEventException() {
        super(EventErrorCode.CANNOT_DELETE_BY_OPEN_EVENT);
    }
}
