package com.ex.ticket.event.exception;

import com.ex.ticket.common.PalagoException;

public class AlreadyDeletedStatusException extends PalagoException {

    public static final PalagoException EXCEPTION = new AlreadyDeletedStatusException();

    private AlreadyDeletedStatusException() {
        super(EventErrorCode.ALREADY_DELETED_STATUS);
    }
}
