package com.projects.nlpostcodesdistancecalculator.presentation.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Route {

    private String travelMode;
    private String distance;
    private String duration;
}
