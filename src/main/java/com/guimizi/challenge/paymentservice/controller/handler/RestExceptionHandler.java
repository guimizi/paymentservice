package com.guimizi.challenge.paymentservice.controller.handler;

import com.guimizi.challenge.paymentservice.model.api.exception.ApiError;
import com.guimizi.challenge.paymentservice.model.api.exception.ApiException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class RestExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleApiException(Exception ex) {
        LOGGER.error(ex.getMessage(), ex);

        return new ResponseEntity<ApiError>(new ApiError(500, "unexpected error", ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
