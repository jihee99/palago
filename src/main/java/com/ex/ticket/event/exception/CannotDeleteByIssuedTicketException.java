package com.ex.ticket.event.exception;

import com.ex.ticket.common.PalagoException;

public class CannotDeleteByIssuedTicketException extends PalagoException {

    public static final PalagoException EXCEPTION = new CannotDeleteByIssuedTicketException();

    private CannotDeleteByIssuedTicketException() {
        super(EventErrorCode.CANNOT_DELETE_BY_ISSUED_TICKET);
    }
}