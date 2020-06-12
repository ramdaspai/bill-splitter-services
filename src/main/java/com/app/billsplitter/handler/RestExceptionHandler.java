package com.app.billsplitter.handler;

import com.app.billsplitter.exception.ErrorResponse;
import com.mongodb.MongoWriteException;
import io.micrometer.core.instrument.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.SocketTimeoutException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
public class RestExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(value = {SocketTimeoutException.class})
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorResponse socketTimeoutException(SocketTimeoutException ex) {
        String errorMsg = "The call to service was timed out by the server.";
        LOGGER.error(errorMsg, ex);
        return new ErrorResponse(INTERNAL_SERVER_ERROR, errorMsg);
    }

    @ExceptionHandler(value = {MongoWriteException.class})
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorResponse contactSearchException(MongoWriteException ex) {
        LOGGER.error(String.valueOf(ex.getCode()));

        if (StringUtils.isNotEmpty(ex.getMessage())
                && ex.getMessage().contains("E11000 duplicate key error")) {
            return new ErrorResponse(INTERNAL_SERVER_ERROR, "Duplicate user found. Phone numbers must be unique to a person.");
        }

        return new ErrorResponse(INTERNAL_SERVER_ERROR, ex.getLocalizedMessage());
    }
}