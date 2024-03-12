package fr.bred.example.interview.controllers;

import fr.bred.example.interview.rest.dtos.City;
import fr.bred.example.interview.services.CitiesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CitiesControllerTest {

    private CitiesService citiesService;
    private CitiesController tested;

    @BeforeEach
    void setUp() {
        citiesService = Mockito.mock(CitiesService.class);
        tested = new CitiesController(citiesService);
    }

    @Test
    void givenNullParams_whenGetCities_thenNoError() {
        //When
        Mockito.when(citiesService.getCities(null, null, null, null, null, null)).thenReturn(List.of());

        //Test
        ResponseEntity<List<City>> cities = tested.getCities(null, null, null, null, null, null);

        //Check
        Mockito.verify(citiesService)
                .getCities(null, null, null, null, null, null);

        assertEquals(200, cities.getStatusCode().value());
        assertNotNull(cities.getBody());
        assertEquals(0, cities.getBody().size());
    }

    @Test
    void givenParams_whenGetCities_thenNoError() {
        //When
        Mockito.when(citiesService.getCities(".*", ".*", 10, 0, "name", "asc"))
                .thenReturn(List.of(new City()));

        //Test
        ResponseEntity<List<City>> cities = tested.getCities(".*", ".*", 10, 0, "name", "asc");

        //Check
        Mockito.verify(citiesService)
                .getCities(".*", ".*", 10, 0, "name", "asc");

        assertEquals(200, cities.getStatusCode().value());
        assertNotNull(cities.getBody());
        assertEquals(1, cities.getBody().size());
    }

    @Test
    void givenGoodParams_whenNearestCity_thenNoError() {
        //When
        Mockito.when(citiesService.findNearestCity("10", "10"))
                .thenReturn(new City());

        //Test
        ResponseEntity<City> nearestCity = tested.nearestCity("10", "10");

        //Check
        Mockito.verify(citiesService)
                .findNearestCity("10", "10");

        assertEquals(200, nearestCity.getStatusCode().value());
        assertNotNull(nearestCity.getBody());
    }

    @Test
    void givenBadParams_whenNearestCity_thenBadRequest() {
        //When
        Mockito.when(citiesService.findNearestCity("10xx", "10xx"))
                .thenThrow(new IllegalArgumentException());

        //Test
        ResponseEntity<City> nearestCity = tested.nearestCity("10xx", "10xx");

        //Check
        Mockito.verify(citiesService)
                .findNearestCity("10xx", "10xx");

        assertEquals(400, nearestCity.getStatusCode().value());
    }

    @Test
    void givenGoodParamsAndSystemError_whenNearestCity_thenInternalServerError() {
        //When
        Mockito.when(citiesService.findNearestCity("10xx", "10xx"))
                .thenThrow(new RuntimeException());

        //Test
        ResponseEntity<City> nearestCity = tested.nearestCity("10xx", "10xx");

        //Check
        Mockito.verify(citiesService)
                .findNearestCity("10xx", "10xx");

        assertEquals(500, nearestCity.getStatusCode().value());
    }
}