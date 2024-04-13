package com.ex.ticket.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
@AllArgsConstructor
public class PalagoException extends RuntimeException{
	public BaseErrorCode errorCode;

	public ErrorReason getErrorReason() {
		return this.errorCode.getErrorReason();
	}
}
