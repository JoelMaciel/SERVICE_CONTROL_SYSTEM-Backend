package com.joelmaciel.service_control.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    SYSTEM_ERROR("/system-error", "System-error"),
    INVALID_DATA("/invalid-data", "Invalid Data"),
    INVALID_PARAMETER("/invalid-parameter", "Invalid Parameter"),
    MESSAGE_INCOMPREHESIBLE("/message-incomprehensible", "Message Incomprehensible"),
    RESOURCE_NOT_FUND("/resource-not-found", "Resource Not Found"),
    ENTITY_IN_USE("/entity-in-use", "Entity In Use"),
    BUSINESS_ERROR("/business-error", "Business Rule Violation");

    private String title;
    private String uri;

    ProblemType(String path, String title) {
        this.uri = "https://service_control.com.br" + path;
        this.title = title;
    }
}
