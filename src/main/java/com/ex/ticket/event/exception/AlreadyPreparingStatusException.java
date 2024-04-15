package com.ex.ticket.event.exception;

import com.ex.ticket.common.domain.PalagoException;

public class AlreadyPreparingStatusException extends PalagoException {

    public static final PalagoException EXCEPTION = new AlreadyPreparingStatusException();

    private AlreadyPreparingStatusException() {
        super(EventErrorCode.ALREADY_PREPARING_STATUS);
    }
}
