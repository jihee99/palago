package com.ex.ticket.user.exception;

import com.ex.ticket.common.PalagoException;

public class AlreadyAppliedUserException extends PalagoException {

	public static final PalagoException EXCEPTION = new AlreadyAppliedUserException();

	private AlreadyAppliedUserException() {
		super(UserErrorCode.ALREADY_APPLIED_USER);
	}

}
