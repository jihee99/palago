package com.ex.ticket.event.exception;

import com.ex.ticket.common.domain.PalagoException;

public class UseAnotherApiException extends PalagoException {
	public static final PalagoException EXCEPTION = new UseAnotherApiException();
	private UseAnotherApiException() {super(EventErrorCode.USE_ANOTHER_API);}
}
