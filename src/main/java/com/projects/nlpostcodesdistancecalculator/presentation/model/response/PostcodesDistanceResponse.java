package com.projects.nlpostcodesdistancecalculator.presentation.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class PostcodesDistanceResponse {

    private PostcodeDTO from;
    private PostcodeDTO destination;
    private Double distance;
    private String unit;
}
