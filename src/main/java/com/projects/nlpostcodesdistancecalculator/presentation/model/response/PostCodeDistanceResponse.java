package com.projects.nlpostcodesdistancecalculator.presentation.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class PostCodeDistanceResponse {

    private String fromPostCode;
    private String destinationPostCode;
    private List<Route> routes;
}
