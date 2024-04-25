package com.ex.ticket.ticketItem.exception;

import com.ex.ticket.common.domain.PalagoException;

public class InvalidTicketTypeException extends PalagoException {

    public static final PalagoException EXCEPTION = new InvalidTicketTypeException();
    private InvalidTicketTypeException() {
        super(TicketItemErrorCode.INVALID_TICKET_TYPE);
    }
}
