package com.projects.nlpostcodesdistancecalculator.presentation.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import static com.projects.nlpostcodesdistancecalculator.presentation.model.ValidationMessages.LONGITUDE_LATITUDE_CANNOT_BE_NULL_OR_EMPTY;


@Data
@AllArgsConstructor
@Builder
public class UpdatePostCodeRequest {

    @NotNull(message = LONGITUDE_LATITUDE_CANNOT_BE_NULL_OR_EMPTY)
    private Double longitude;

    @NotNull(message = LONGITUDE_LATITUDE_CANNOT_BE_NULL_OR_EMPTY)
    private Double latitude;
}
