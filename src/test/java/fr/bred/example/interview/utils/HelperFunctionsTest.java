package fr.bred.example.interview.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HelperFunctionsTest {

    @Test
    void givenGoodParam_whenErrorSafeDoubleToString_thenSuccess() {
        Double result = HelperFunctions.errorSafeDoubleToString("10");
        assertNotNull(result);
        assertEquals(10.0, result);
    }

    @Test
    void givenBadParam_whenErrorSafeDoubleToString_thenNull() {
        Double result = HelperFunctions.errorSafeDoubleToString("10a");
        assertNull(result);

        assertNull(HelperFunctions.errorSafeDoubleToString(null));
    }

    @Test
    void givenZero_whenCalculateDistanceFromCoordinates_thenSuccess() {
        double result = HelperFunctions.calculateDistanceFromCoordinates(0, 0, 0, 0);
        assertEquals(0, result);
    }

    @Test
    void givenGoodCoordinates_whenCalculateDistanceFromCoordinates_thenSuccess() {
        double result = HelperFunctions.calculateDistanceFromCoordinates(2.3522, 48.8566, 2.3522, 48.8566);

        assertEquals(0, result, 0.001);
    }
}