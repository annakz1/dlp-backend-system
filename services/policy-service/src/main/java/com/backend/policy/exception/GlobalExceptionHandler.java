package com.backend.policy.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        Map<String, Object> extras = new HashMap<>();
        extras.put("errors", errors);
        return buildResponse(HttpStatus.BAD_REQUEST, "Validation failed", extras);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Object> handleResponseStatusException(ResponseStatusException ex, WebRequest request) {
        return buildResponse(ex.getStatusCode(), ex.getReason(), null);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), null);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex) {
        // Catch-all fallback for any other exceptions
        logger.error("Unhandled exception", ex);
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", ex.getMessage() != null ? Map.of("error", ex.getMessage()) : null);
    }

    private ResponseEntity<Object> buildResponse(HttpStatusCode status, String message, Map<String, Object> extras) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("message", message);
        if (extras != null) body.putAll(extras);
        return new ResponseEntity<>(body, status);
    }
}
