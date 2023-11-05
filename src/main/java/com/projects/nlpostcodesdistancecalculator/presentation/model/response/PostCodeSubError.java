package com.projects.nlpostcodesdistancecalculator.presentation.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostCodeSubError {

    private String message;
}
