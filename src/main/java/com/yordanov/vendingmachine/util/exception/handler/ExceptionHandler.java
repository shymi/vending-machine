package com.yordanov.vendingmachine.util.exception.handler;

import com.yordanov.vendingmachine.util.exception.error.ApiError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandler.class);

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        LOGGER.error(ex.getMessage(), ex);

        FieldError err = ex.getBindingResult().getFieldError();
        String msg = err != null ? err.getDefaultMessage() : "Invalid request";
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, msg), HttpStatus.BAD_REQUEST);
    }

    protected ResponseEntity<Object> handleTypeMismatch(
            TypeMismatchException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        String msg = "Invalid uri request parameters";
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, msg), HttpStatus.BAD_REQUEST);
    }

    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        String msg = "Invalid request - please check sent values";
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, msg), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError, HttpStatus status) {
        return new ResponseEntity<>(apiError, status);
    }
}
