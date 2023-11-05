package com.projects.nlpostcodesdistancecalculator.service.calculator;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.projects.nlpostcodesdistancecalculator.presentation.model.request.PostcodesDistanceRequest;
import com.projects.nlpostcodesdistancecalculator.presentation.model.response.PostcodeDTO;
import com.projects.nlpostcodesdistancecalculator.presentation.model.response.PostcodesDistanceResponse;
import com.projects.nlpostcodesdistancecalculator.service.exception.DistanceCalculationException;
import com.projects.nlpostcodesdistancecalculator.service.utils.DistanceCalculatorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DistanceCalculatorServiceImpl implements DistanceCalculatorService {

    private final Logger logger = LoggerFactory.getLogger(DistanceCalculatorServiceImpl.class);

    @Value("${google.maps.api.key}")
    private String apiKey;

    @Override
    public PostcodesDistanceResponse calculateDistance(PostcodesDistanceRequest request) throws DistanceCalculationException {
        PostcodeDTO fromPostCode = getCoordinatesForPostcode(request.getFromPostCode());
        PostcodeDTO destinationPostCode = getCoordinatesForPostcode(request.getDestinationPostCode());
        Double distance = DistanceCalculatorUtils.calculateDistance(fromPostCode.getLatitude(), fromPostCode.getLongitude(),
                destinationPostCode.getLatitude(), destinationPostCode.getLongitude());
        return PostcodesDistanceResponse.builder()
                .from(fromPostCode)
                .destination(destinationPostCode)
                .distance(distance)
                .unit("km")
                .build();
    }

    private PostcodeDTO getCoordinatesForPostcode(String postCode) throws DistanceCalculationException {
        try(GeoApiContext context = new GeoApiContext.Builder().apiKey(apiKey).build()) {
            GeocodingApiRequest request = GeocodingApi.geocode(context, postCode);
            GeocodingResult[] results = request.await();

            LatLng location = results[0].geometry.location;
            return PostcodeDTO.builder().latitude(location.lat).longitude(location.lng).postCode(postCode).build();

        } catch (Exception e) {
            logger.warn("Could not get the coordinates for postCode {} due to {}", postCode, e.getMessage());
            throw new DistanceCalculationException(String.format("Could not get the coordinates for postCode %s", postCode));
        }
    }
}
