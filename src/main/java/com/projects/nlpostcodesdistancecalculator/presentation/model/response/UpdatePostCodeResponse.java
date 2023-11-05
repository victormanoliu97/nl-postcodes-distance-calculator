package com.projects.nlpostcodesdistancecalculator.presentation.model.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdatePostCodeResponse {

    private String message;
    private String postCode;
    private Double longitude;
    private Double latitude;
}
