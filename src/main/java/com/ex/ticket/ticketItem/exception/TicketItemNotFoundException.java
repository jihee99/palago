package com.ex.ticket.ticketItem.exception;

import com.ex.ticket.common.domain.PalagoException;

public class TicketItemNotFoundException extends PalagoException {

    public static final PalagoException EXCEPTION = new TicketItemNotFoundException();

    private TicketItemNotFoundException() {
        super(TicketItemErrorCode.TICKET_ITEM_NOT_FOUND);
    }
}
