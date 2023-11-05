package com.projects.nlpostcodesdistancecalculator.presentation;

import com.projects.nlpostcodesdistancecalculator.presentation.model.request.PostcodesDistanceRequest;
import com.projects.nlpostcodesdistancecalculator.presentation.model.request.UpdatePostCodeRequest;
import com.projects.nlpostcodesdistancecalculator.presentation.model.response.PostCodeDistanceResponse;
import com.projects.nlpostcodesdistancecalculator.presentation.model.response.PostcodesDistanceResponse;
import com.projects.nlpostcodesdistancecalculator.presentation.model.response.UpdatePostCodeResponse;
import com.projects.nlpostcodesdistancecalculator.service.PostCodesService;
import com.projects.nlpostcodesdistancecalculator.service.calculator.DistanceCalculatorService;
import com.projects.nlpostcodesdistancecalculator.service.directions.DirectionsComputerService;
import com.projects.nlpostcodesdistancecalculator.service.exception.DistanceCalculationException;
import com.projects.nlpostcodesdistancecalculator.service.exception.PostCodeNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.projects.nlpostcodesdistancecalculator.config.LogKeys.*;

@RestController
@RequestMapping(value = "/postcodes", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class PostcodeController {

    private final Logger logger = LoggerFactory.getLogger(PostcodeController.class);

    private final DistanceCalculatorService distanceCalculatorService;
    private final DirectionsComputerService directionsComputerService;
    private final PostCodesService postCodesService;

    @PostMapping(value = "/calculate-distance", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostcodesDistanceResponse> calculateDistance(@RequestBody @Validated PostcodesDistanceRequest request) throws DistanceCalculationException {
        logger.atInfo()
                .addKeyValue(FROM_POSTCODE_KEY, request.getFromPostCode())
                .addKeyValue(DESTINATION_POSTCODE_KEY, request.getDestinationPostCode())
                .log("Starting the distance calculation");
        PostcodesDistanceResponse response = distanceCalculatorService.calculateDistance(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/update/{postCode}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updatePostCode(@PathVariable("postCode") String postCode, @RequestBody @Validated UpdatePostCodeRequest request)
            throws PostCodeNotFoundException {
        logger.atInfo()
                .addKeyValue(POSTCODE_TO_BE_UPDATED_KEY, postCode)
                .addKeyValue(LONGITUDE_KEY, request.getLongitude())
                .addKeyValue(LATITUDE_KEY, request.getLatitude())
                .log("Starting the update of postcode");
        UpdatePostCodeResponse responseBody = postCodesService.updatePostCode(postCode, request);
        return ResponseEntity.ok(responseBody);
    }

    @PostMapping("/routes")
    public ResponseEntity<Object> computeDirections(@RequestBody @Validated PostcodesDistanceRequest request) {
        logger.atInfo()
                .addKeyValue(FROM_POSTCODE_KEY, request.getFromPostCode())
                .addKeyValue(DESTINATION_POSTCODE_KEY, request.getDestinationPostCode())
                .log("Starting the directions computation");
        PostCodeDistanceResponse responseBody = directionsComputerService.computeDirectionsForPostCodes(request);
        return ResponseEntity.ok(responseBody);
    }
}
