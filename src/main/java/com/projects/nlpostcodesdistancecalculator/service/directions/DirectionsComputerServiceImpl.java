package com.projects.nlpostcodesdistancecalculator.service.directions;

import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.TravelMode;
import com.projects.nlpostcodesdistancecalculator.presentation.model.request.PostcodesDistanceRequest;
import com.projects.nlpostcodesdistancecalculator.presentation.model.response.PostCodeDistanceResponse;
import com.projects.nlpostcodesdistancecalculator.presentation.model.response.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
public class DirectionsComputerServiceImpl implements DirectionsComputerService {

    private final Logger logger = LoggerFactory.getLogger(DirectionsComputerServiceImpl.class);

    @Value("${google.maps.api.key}")
    private String apiKey;

    @Override
    public PostCodeDistanceResponse computeDirectionsForPostCodes(PostcodesDistanceRequest request) {
        try(GeoApiContext context = new GeoApiContext.Builder().apiKey(apiKey).build()) {
            CompletableFuture<DirectionsResult> bikingFuture = CompletableFuture.supplyAsync(() ->
                    getDirections(context, request.getFromPostCode(), request.getDestinationPostCode(), TravelMode.BICYCLING));
            CompletableFuture<DirectionsResult> drivingFuture = CompletableFuture.supplyAsync(() ->
                    getDirections(context, request.getFromPostCode(), request.getDestinationPostCode(), TravelMode.DRIVING));
            CompletableFuture<DirectionsResult> transitFuture = CompletableFuture.supplyAsync(() ->
                    getDirections(context, request.getFromPostCode(), request.getDestinationPostCode(), TravelMode.TRANSIT));
            CompletableFuture<DirectionsResult> walkingFuture = CompletableFuture.supplyAsync(() ->
                    getDirections(context, request.getFromPostCode(), request.getDestinationPostCode(), TravelMode.WALKING));

            CompletableFuture<Void> allTasks = CompletableFuture.allOf(bikingFuture, drivingFuture, transitFuture, walkingFuture);
            allTasks.join();

            DirectionsResult directionsBiking = bikingFuture.join();
            DirectionsResult directionsDriving = drivingFuture.join();
            DirectionsResult directionsTransit = transitFuture.join();
            DirectionsResult directionsWalking = walkingFuture.join();

            List<Route> bikingRoutes = computeRoute(directionsBiking, TravelMode.BICYCLING);
            List<Route> drivingRoutes = computeRoute(directionsDriving, TravelMode.DRIVING);
            List<Route> transitRoutes = computeRoute(directionsTransit, TravelMode.TRANSIT);
            List<Route> walkingRoutes = computeRoute(directionsWalking, TravelMode.WALKING);

            List<Route> finalRoutes = new ArrayList<>();
            finalRoutes.addAll(bikingRoutes);
            finalRoutes.addAll(drivingRoutes);
            finalRoutes.addAll(transitRoutes);
            finalRoutes.addAll(walkingRoutes);

            return PostCodeDistanceResponse.builder()
                    .fromPostCode(request.getFromPostCode())
                    .destinationPostCode(request.getDestinationPostCode())
                    .routes(finalRoutes)
                    .build();
        }
        catch (Exception e) {
            logger.warn("Failed");
            return null;
        }
    }

    private List<Route> computeRoute(DirectionsResult directionsResult, TravelMode travelMode) {
        if(Objects.nonNull(directionsResult)) {
            List<Route> routeList = new ArrayList<>();
            Arrays.stream(directionsResult.routes).forEach(directionsRoute -> {
                Arrays.stream(directionsRoute.legs).forEach(directionsLeg -> {
                    Route route = Route.builder()
                            .distance(directionsLeg.distance.humanReadable)
                            .duration(directionsLeg.duration.humanReadable)
                            .travelMode(travelMode.toString())
                            .build();
                    routeList.add(route);
                });
            });
            return routeList;
        }
        return Collections.emptyList();
    }

    private DirectionsResult getDirections(GeoApiContext context, String origin, String destination, TravelMode mode) {
        try {
            return DirectionsApi.newRequest(context)
                    .mode(mode)
                    .origin(origin)
                    .destination(destination)
                    .await();
        }
        catch (Exception e) {
            logger.atWarn().log("Could not compute directions from {} to {} for travel mode: {}", origin, destination, mode.name());
            return null;
        }
    }
}
