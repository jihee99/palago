package com.ex.ticket.event.exception;

import com.ex.ticket.common.domain.PalagoException;

public class EventNotOpenException extends PalagoException {

    public static final PalagoException EXCEPTION = new EventNotOpenException();

    private EventNotOpenException() {
        super(EventErrorCode.EVENT_NOT_OPEN);
    }
}
