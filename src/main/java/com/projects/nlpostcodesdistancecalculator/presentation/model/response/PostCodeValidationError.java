package com.projects.nlpostcodesdistancecalculator.presentation.model.response;

import lombok.Getter;

@Getter
public class PostCodeValidationError extends PostCodeSubError {

    private String fieldName;
    private Object rejectedValue;
    public PostCodeValidationError(String message) {
        super(message);
    }
    public PostCodeValidationError(String fieldName, Object rejectedValue, String message) {
        super(message);
        this.fieldName = fieldName;
        this.rejectedValue = rejectedValue;
    }
}
