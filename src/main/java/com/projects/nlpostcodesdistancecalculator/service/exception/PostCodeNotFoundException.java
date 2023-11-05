package com.projects.nlpostcodesdistancecalculator.service.exception;

import lombok.Getter;

@Getter
public class PostCodeNotFoundException extends Exception {

    private final String postCode;

    public PostCodeNotFoundException(String message, String postCode) {
        super(message);
        this.postCode = postCode;
    }
}
