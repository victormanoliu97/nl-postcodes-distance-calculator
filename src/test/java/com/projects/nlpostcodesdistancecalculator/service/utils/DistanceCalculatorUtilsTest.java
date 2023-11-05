package com.projects.nlpostcodesdistancecalculator.service.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DistanceCalculatorUtilsTest {

    @Test
    public void calculatesDistanceSuccessfully() {
        double latitude = 51.88760463;
        double longitude = 5.597723367;
        double latitude2 = 52.25902055;
        double longitude2 = 4.869899155;
        double expectedResult = 64.65333475903802;

        double actualResult = DistanceCalculatorUtils.calculateDistance(latitude, longitude, latitude2, longitude2);
        Assertions.assertEquals(expectedResult, actualResult);
    }
}