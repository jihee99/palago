package com.ex.ticket.ticket.exception;

import com.ex.ticket.common.domain.PalagoException;

public class TicketPurchaseLimitException extends PalagoException {

    public static final PalagoException EXCEPTION = new TicketPurchaseLimitException();

    private TicketPurchaseLimitException(){
        super(TicketItemErrorCode.TICKET_ITEM_PURCHASE_LIMIT);
    }
}
