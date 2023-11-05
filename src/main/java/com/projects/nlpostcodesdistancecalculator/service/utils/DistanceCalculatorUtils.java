package com.projects.nlpostcodesdistancecalculator.service.utils;

public class DistanceCalculatorUtils {

    private final static double EARTH_RADIUS = 6371;

    public static Double calculateDistance(double latitude, double longitude, double latitude2, double longitude2) {
        double longitudeRadians = Math.toRadians(longitude);
        double longitude2Radians = Math.toRadians(longitude2);
        double latitudeRadians = Math.toRadians(latitude);
        double latitude2Radians = Math.toRadians(latitude2);

        double squaredSinOfLatitudes = Math.pow(Math.sin((latitude2Radians - latitudeRadians) / 2),  2);
        double squaredSinOfLongitudes = Math.pow(Math.sin((longitude2Radians - longitudeRadians) / 2),  2);

        return 2 * EARTH_RADIUS * Math.asin(Math.sqrt(squaredSinOfLatitudes + Math.cos(latitudeRadians) * Math.cos(latitude2Radians) * squaredSinOfLongitudes));
    }
}
