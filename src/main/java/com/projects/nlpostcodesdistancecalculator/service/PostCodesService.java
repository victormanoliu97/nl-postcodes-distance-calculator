package com.projects.nlpostcodesdistancecalculator.service;

import com.projects.nlpostcodesdistancecalculator.presentation.model.request.UpdatePostCodeRequest;
import com.projects.nlpostcodesdistancecalculator.presentation.model.response.UpdatePostCodeResponse;
import com.projects.nlpostcodesdistancecalculator.service.exception.PostCodeNotFoundException;

public interface PostCodesService {

    UpdatePostCodeResponse updatePostCode(String postCode, UpdatePostCodeRequest request) throws PostCodeNotFoundException;
}
