package com.ex.ticket.common;


public interface BaseErrorCode {

    public ErrorReason getErrorReason();

    String getExplainError() throws NoSuchFieldException;
}
