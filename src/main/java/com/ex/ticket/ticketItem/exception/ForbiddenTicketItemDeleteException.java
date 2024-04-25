package com.ex.ticket.ticketItem.exception;

import com.ex.ticket.common.domain.PalagoException;

public class ForbiddenTicketItemDeleteException extends PalagoException {

    public static final PalagoException EXCEPTION = new ForbiddenTicketItemDeleteException();

    private ForbiddenTicketItemDeleteException() {
        super(TicketItemErrorCode.FORBIDDEN_TICKET_ITEM_DELETE);
    }
}
