package com.guimizi.challenge.paymentservice.model.api.exception;

public enum ErrorType {
    INVALID_EVENT_TYPE(400, 1, "Event type not supporter"),
    CHARGE_NOT_ALLOWED(400, 2, "Charge dont belong to this month"),
    CHARGE_ALREADY_EXISTS(400, 3, "Charge already exists"),
    NOT_SUPPORTED_CURRENCY_EXCEPTION(400, 4, "Currency not supported"),
    INVALID_AMOUNT_VALUE(400, 5, "Amount should be positive");

    private int httpCode;
    private int errorCode;
    private String message;

    ErrorType(int httpCode, int errorCode, String message) {
        this.httpCode = httpCode;
        this.errorCode = errorCode;
        this.message = message;
    }

    public int getErrorCode() {
        return this.httpCode * 1000 + errorCode;
    }

    public int getHttpCode() {
        return httpCode;
    }

    public String getMessage() {
        return message;
    }
}
