package com.ex.ticket.event.exception;

import com.ex.ticket.common.domain.PalagoException;

public class AlreadyOpenStatusException extends PalagoException {

    public static final PalagoException EXCEPTION = new AlreadyOpenStatusException();

    private AlreadyOpenStatusException() {
        super(EventErrorCode.ALREADY_OPEN_STATUS);
    }
}
