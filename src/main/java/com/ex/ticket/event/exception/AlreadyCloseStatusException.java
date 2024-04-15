package com.ex.ticket.event.exception;

import com.ex.ticket.common.PalagoException;

public class AlreadyCloseStatusException extends PalagoException {

    public static final PalagoException EXCEPTION = new AlreadyCloseStatusException();

    private AlreadyCloseStatusException() {
        super(EventErrorCode.ALREADY_CLOSE_STATUS);
    }
}
