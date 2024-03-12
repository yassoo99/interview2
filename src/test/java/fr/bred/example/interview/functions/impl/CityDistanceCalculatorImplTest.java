package fr.bred.example.interview.functions.impl;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import fr.bred.example.interview.rest.dtos.Point;

class CityDistanceCalculatorImplTest {

    private final CityDistanceCalculatorImpl tested = new CityDistanceCalculatorImpl();


    @Test
    void givenGoodParams_whenCalculateDistanceToCity_thenSuccess() {

        Point p1 = new Point();
        p1.setX("10.0");
        p1.setY("10.0");

        Point p2 = new Point();
        p2.setX("0.0");
        p2.setY("0.0");

        double distance = tested.calculateDistanceToCity(p1, p2);

        MatcherAssert.assertThat(distance, Matchers.closeTo(14.14, 0.01));
    }

}