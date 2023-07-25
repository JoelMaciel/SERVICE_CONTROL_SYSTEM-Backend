package com.joelmaciel.service_control.domain.exception;

public class EmailAlreadyExistsException extends EntityInUseException {
    public EmailAlreadyExistsException(String email) {
        super(String.format("There is already a client registered with this Email : %s  ", email));
    }
}
