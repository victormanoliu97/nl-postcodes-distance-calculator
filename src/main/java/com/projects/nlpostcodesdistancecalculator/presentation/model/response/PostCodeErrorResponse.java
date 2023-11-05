package com.projects.nlpostcodesdistancecalculator.presentation.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class PostCodeErrorResponse {

    private Instant time;
    private List<PostCodeSubError> errors;
}
