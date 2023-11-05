package com.projects.nlpostcodesdistancecalculator.presentation.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class PostcodeDTO {

    private String postCode;
    private Double longitude;
    private Double latitude;
}
