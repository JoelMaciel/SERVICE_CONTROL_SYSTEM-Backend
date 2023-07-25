package com.joelmaciel.service_control.domain.exception;

public class EntityNotExistsException extends BusinessException{
    public EntityNotExistsException(String message) {
        super(message);
    }
}
