package com.joelmaciel.service_control.domain.exception;

public class InvalidLoginDataException extends BusinessException {
    public InvalidLoginDataException(String message) {
        super(message);
    }
}
