package com.projects.nlpostcodesdistancecalculator.presentation.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import static com.projects.nlpostcodesdistancecalculator.presentation.model.ValidationMessages.POSTCODE_CANNOT_BE_NULL_OR_EMPTY;

@Data
@AllArgsConstructor
@Builder
public class PostcodesDistanceRequest {

    @NotBlank(message = POSTCODE_CANNOT_BE_NULL_OR_EMPTY)
    private String fromPostCode;

    @NotBlank(message = POSTCODE_CANNOT_BE_NULL_OR_EMPTY)
    private String destinationPostCode;
}
