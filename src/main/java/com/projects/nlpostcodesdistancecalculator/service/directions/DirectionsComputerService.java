package com.projects.nlpostcodesdistancecalculator.service.directions;

import com.projects.nlpostcodesdistancecalculator.presentation.model.request.PostcodesDistanceRequest;
import com.projects.nlpostcodesdistancecalculator.presentation.model.response.PostCodeDistanceResponse;

public interface DirectionsComputerService {

    PostCodeDistanceResponse computeDirectionsForPostCodes(PostcodesDistanceRequest request);
}
