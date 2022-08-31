package com.embea.policy.services.policy_service.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    public static final String SERVER_SIDE_UNKNOWN_EXCEPTION = "SERVER_SIDE_UNKNOWN_EXCEPTION";
    public static final String REQUEST_VALIDATION_EXCEPTION = "REQUEST_VALIDATION_EXCEPTION";

    @ExceptionHandler({Exception.class})
    public final ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex, WebRequest request) {
        log.error("Unknown error caught", ex);
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse errorResponse = new ErrorResponse(SERVER_SIDE_UNKNOWN_EXCEPTION, details);
        return ResponseEntity.ok(errorResponse);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingRequestHeaderException.class)
    public final ResponseEntity<ErrorResponse> handleValidationExceptions(Exception ex, WebRequest request) {
        log.error("Validation exception", ex);
        List<String> details = new ArrayList<>();
        details.add(ex.getCause().getMessage());
        ErrorResponse errorResponse = new ErrorResponse(REQUEST_VALIDATION_EXCEPTION, details);
        return ResponseEntity.badRequest().body(errorResponse);
    }

}
