package com.ex.ticket.group.exception;

import com.ex.ticket.common.domain.PalagoException;

public class CannotModifyMasterGroupUserRoleException extends PalagoException {

    public static final PalagoException EXCEPTION = new CannotModifyMasterGroupUserRoleException();

    private CannotModifyMasterGroupUserRoleException() {
        super(GroupErrorCode.CANNOT_MODIFY_MASTER_GROUP_ROLE);
    }
}
