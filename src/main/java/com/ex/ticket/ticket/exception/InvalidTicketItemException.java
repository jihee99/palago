package com.ex.ticket.ticket.exception;

import com.ex.ticket.common.domain.PalagoException;

public class InvalidTicketItemException extends PalagoException {

    public static final PalagoException EXCEPTION = new InvalidTicketItemException();

    private InvalidTicketItemException() {
        super(TicketItemErrorCode.INVALID_TICKET_ITEM);
    }
}
