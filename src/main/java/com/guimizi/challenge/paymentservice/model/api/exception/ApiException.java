package com.guimizi.challenge.paymentservice.model.api.exception;

public class ApiException extends RuntimeException {

    private int httpCode;
    private int errorCode;

    public ApiException(ErrorType errorType) {
        super(errorType.getMessage());
        this.httpCode = errorType.getHttpCode();
        this.errorCode = errorType.getErrorCode();
    }

    public int getHttpCode() {
        return httpCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
