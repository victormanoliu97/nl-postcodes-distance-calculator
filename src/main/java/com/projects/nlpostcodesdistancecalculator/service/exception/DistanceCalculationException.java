package com.projects.nlpostcodesdistancecalculator.service.exception;

import lombok.Getter;

@Getter
public class DistanceCalculationException extends Exception {

    public DistanceCalculationException(String message) {
        super(message);
    }
}
