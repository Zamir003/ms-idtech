package com.project.ms_idtech.exception;

import com.project.ms_idtech.exception.custom.BadRequestException;
import com.project.ms_idtech.exception.custom.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    public record ErrorResponse(
            Instant timestamp,
            int status,
            String error,
            String message,
            Map<String, String> fieldErrors
    ) { }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse notFound(NotFoundException ex) {
        return new ErrorResponse(Instant.now(), 404, "NOT_FOUND", ex.getMessage(), null);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse badRequest(BadRequestException ex) {
        return new ErrorResponse(Instant.now(), 400, "BAD_REQUEST", ex.getMessage(), null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse validation(MethodArgumentNotValidException ex) {
        Map<String, String> map = new HashMap<>();
        for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
            map.put(fe.getField(), fe.getDefaultMessage());
        }
        return new ErrorResponse(Instant.now(), 400, "VALIDATION_ERROR", "Validation failed", map);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse other(Exception ex) {
        return new ErrorResponse(Instant.now(), 500, "INTERNAL_ERROR", ex.getMessage(), null);
    }
}