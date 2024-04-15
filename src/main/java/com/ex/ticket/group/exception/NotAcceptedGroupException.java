package com.ex.ticket.group.exception;

import com.ex.ticket.common.domain.PalagoException;

public class NotAcceptedGroupException extends PalagoException {

    public static final PalagoException EXCEPTION = new NotAcceptedGroupException();

    private NotAcceptedGroupException() {
        super(GroupErrorCode.NOT_ACCEPTED_GROUP);
    }
}
