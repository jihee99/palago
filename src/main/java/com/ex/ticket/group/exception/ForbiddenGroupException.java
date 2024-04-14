package com.ex.ticket.group.exception;

import com.ex.ticket.common.PalagoException;

public class ForbiddenGroupException extends PalagoException {

    public static final PalagoException EXCEPTION = new ForbiddenGroupException();

    private ForbiddenGroupException() {
        super(GroupErrorCode.FORBIDDEN_GROUP);
    }
}
