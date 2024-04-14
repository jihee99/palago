package com.ex.ticket.group.exception;

import com.ex.ticket.common.PalagoException;

public class GroupUserNotFoundException extends PalagoException {

    public static final PalagoException EXCEPTION = new GroupUserNotFoundException();

    private GroupUserNotFoundException() {
        super(GroupErrorCode.GROUP_USER_NOT_FOUND);
    }
}
