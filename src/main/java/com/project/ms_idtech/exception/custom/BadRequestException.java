package com.project.ms_idtech.exception.custom;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) { super(message); }
}