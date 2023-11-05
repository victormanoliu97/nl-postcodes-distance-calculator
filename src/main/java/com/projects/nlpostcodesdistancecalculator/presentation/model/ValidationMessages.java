package com.projects.nlpostcodesdistancecalculator.presentation.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ValidationMessages {

    public static final String POSTCODE_CANNOT_BE_NULL_OR_EMPTY = "Postcode cannot be null or empty";
    public static final String LONGITUDE_LATITUDE_CANNOT_BE_NULL_OR_EMPTY = "Longitude or latitude cannot be null or empty";
}
