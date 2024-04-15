package com.ex.ticket.common.domain;

import com.ex.ticket.common.domain.BaseErrorCode;
import com.ex.ticket.common.domain.ErrorReason;
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
