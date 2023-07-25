package com.joelmaciel.service_control.domain.exception;

public class UsernameAlreadyExistsException extends EntityInUseException {
    public UsernameAlreadyExistsException(String username) {
        super(String.format("There is already a client registered with this Username : %s  ", username));
    }
}
