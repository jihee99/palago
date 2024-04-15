package com.ex.ticket.common.domain;


public interface BaseErrorCode {

    public ErrorReason getErrorReason();

    String getExplainError() throws NoSuchFieldException;
}
