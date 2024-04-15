package com.ex.ticket.event.exception;

import com.ex.ticket.common.PalagoException;

public class EventNotFoundException extends PalagoException {

    public static final PalagoException EXCEPTION = new EventNotFoundException();

    private EventNotFoundException() {
        super(EventErrorCode.EVENT_NOT_FOUND);
    }
}
