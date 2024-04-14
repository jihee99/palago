package com.ex.ticket.group.exception;

import com.ex.ticket.common.PalagoException;

public class NotManagerGroupException extends PalagoException {

    public static final PalagoException EXCEPTION = new NotManagerGroupException();

    private NotManagerGroupException() {
        super(GroupErrorCode.NOT_MANAGER_GROUP);
    }
}
