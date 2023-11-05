package com.projects.nlpostcodesdistancecalculator.service;

import com.projects.nlpostcodesdistancecalculator.persistance.entity.Postcode;
import com.projects.nlpostcodesdistancecalculator.persistance.repository.PostcodeRepository;
import com.projects.nlpostcodesdistancecalculator.presentation.model.request.UpdatePostCodeRequest;
import com.projects.nlpostcodesdistancecalculator.presentation.model.response.UpdatePostCodeResponse;
import com.projects.nlpostcodesdistancecalculator.service.exception.PostCodeNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostCodesServiceImpl implements PostCodesService {

    private final PostcodeRepository postcodeRepository;

    @Override
    public UpdatePostCodeResponse updatePostCode(String postCode, UpdatePostCodeRequest request) throws PostCodeNotFoundException {
        return postcodeRepository.findByPostCode(postCode).map(foundPostCode -> {
                    Postcode postCodeToBeUpdated = Postcode.builder()
                            .postCodeID(foundPostCode.getPostCodeID())
                            .postCode(foundPostCode.getPostCode())
                            .latitude(request.getLatitude())
                            .longitude(request.getLongitude())
                            .build();

                    Postcode updatedPostCode = postcodeRepository.save(postCodeToBeUpdated);

                    UpdatePostCodeResponse response = new UpdatePostCodeResponse();
                    response.setMessage("coordinates are successfully updated");
                    response.setPostCode(updatedPostCode.getPostCode());
                    response.setLatitude(updatedPostCode.getLatitude());
                    response.setLongitude(updatedPostCode.getLongitude());

                    return response;
        }).orElseThrow(() -> new PostCodeNotFoundException(String.format("could not find postCode %s", postCode), postCode));
    }
}
