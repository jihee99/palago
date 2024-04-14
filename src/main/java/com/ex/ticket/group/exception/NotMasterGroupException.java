package com.ex.ticket.group.exception;

import com.ex.ticket.common.PalagoException;

public class NotMasterGroupException extends PalagoException {

    public static final PalagoException EXCEPTION = new NotMasterGroupException();

    private NotMasterGroupException() {
        super(GroupErrorCode.NOT_MASTER_GROUP);
    }
}
