package com.ex.ticket.ticket.exception;

import com.ex.ticket.common.domain.PalagoException;

public class TicketItemQuantityException extends PalagoException {

    public static final PalagoException EXCEPTION = new TicketItemQuantityException();

    private TicketItemQuantityException() {
        super(TicketItemErrorCode.TICKET_ITEM_QUANTITY_LESS_THAN_ZERO);
    }
}
