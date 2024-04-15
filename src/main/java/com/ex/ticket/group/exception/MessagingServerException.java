package com.ex.ticket.group.exception;

import com.ex.ticket.common.domain.PalagoException;

public class MessagingServerException extends PalagoException {

    public static final PalagoException EXCEPTION = new MessagingServerException();

    public MessagingServerException() {
        super(GroupErrorCode.MESSAGING_SERVER_EXCEPTION);
    }
}
