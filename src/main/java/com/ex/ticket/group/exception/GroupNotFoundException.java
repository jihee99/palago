package com.ex.ticket.group.exception;

import com.ex.ticket.common.domain.PalagoException;

public class GroupNotFoundException extends PalagoException {

    public static final PalagoException EXCEPTION = new GroupNotFoundException();

    private GroupNotFoundException() {
        super(GroupErrorCode.GROUP_NOT_FOUND);
    }
}
