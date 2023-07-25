package com.joelmaciel.service_control.api.dto.validator;

public interface Validator<E>{

    void validate(E e);
}
