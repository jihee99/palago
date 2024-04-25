package com.ex.ticket.ticketItem.exception;

import com.ex.ticket.common.domain.PalagoException;

public class InvalidTicketPriceException extends PalagoException {

    public static final PalagoException EXCEPTION = new InvalidTicketPriceException();
    private InvalidTicketPriceException() {
        super(TicketItemErrorCode.INVALID_TICKET_PRICE);
    }
}
