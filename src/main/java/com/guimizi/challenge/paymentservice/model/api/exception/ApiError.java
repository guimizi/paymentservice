package com.guimizi.challenge.paymentservice.model.api.exception;

public class ApiError {

    private int errorCode;
    private String message;
    private String causedBy;

    public ApiError(int errorCode, String message, String causedBy) {
        this.errorCode = errorCode;
        this.message = message;
        this.causedBy = causedBy;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    public String getCausedBy() {
        return causedBy;
    }
}

