package com.ex.ticket.group.exception;

import com.ex.ticket.common.PalagoException;

public class AlreadyJoinedGroupException extends PalagoException {

    public static final PalagoException EXCEPTION = new AlreadyJoinedGroupException();

    private AlreadyJoinedGroupException() {
        super(GroupErrorCode.ALREADY_JOINED_GROUP);
    }
}
