package com.ex.ticket.event.exception;

import com.ex.ticket.common.domain.PalagoException;

public class EventOpenTimeExpiredException extends PalagoException {

    public static final PalagoException EXCEPTION = new EventOpenTimeExpiredException();

    private EventOpenTimeExpiredException() {
        super(EventErrorCode.OPEN_TIME_EXPIRED);
    }
}
