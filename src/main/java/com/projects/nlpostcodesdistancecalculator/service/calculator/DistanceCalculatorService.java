package com.projects.nlpostcodesdistancecalculator.service.calculator;

import com.projects.nlpostcodesdistancecalculator.presentation.model.request.PostcodesDistanceRequest;
import com.projects.nlpostcodesdistancecalculator.presentation.model.response.PostcodesDistanceResponse;
import com.projects.nlpostcodesdistancecalculator.service.exception.DistanceCalculationException;

public interface DistanceCalculatorService {

    PostcodesDistanceResponse calculateDistance(PostcodesDistanceRequest request) throws DistanceCalculationException;
}
