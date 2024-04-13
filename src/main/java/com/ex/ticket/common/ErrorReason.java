package com.ex.ticket.common;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorReason {

    private final HttpStatus httpStatus;

    private final int errorCode;

    private final String errorMessage;

}
