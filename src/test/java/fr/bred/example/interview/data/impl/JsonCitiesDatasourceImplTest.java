package fr.bred.example.interview.data.impl;

import fr.bred.example.interview.data.CitiesSearchQuery;
import fr.bred.example.interview.rest.dtos.City;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonCitiesDatasourceImplTest {

    private final JsonCitiesDatasourceImpl tested = new JsonCitiesDatasourceImpl();

    @Test
    void testFetchAllCities() {
        List<City> cities = tested.fetchCities();
        assertEquals(5, cities.size());
    }

    @Test
    void givenNoParams_whenFetchCities_thenReturnAll() {
        CitiesSearchQuery query = CitiesSearchQuery.builder().build();
        List<City> cities = tested.fetchCities(query);
        assertEquals(5, cities.size());
    }

    @Test
    void givenLimitAndSort_whenFetchCities_thenReturnLimited() {
        CitiesSearchQuery query = CitiesSearchQuery.builder()
                .limit(2).start(2).order("desc").sort("name").build();
        List<City> cities = tested.fetchCities(query);
        assertEquals(2, cities.size());
        assertEquals("PARIS 11", cities.get(0).getName());
        assertEquals("MARSEILLE", cities.get(1).getName());
    }

    @Test
    void givenNamePattern_whenFetchCities_thenReturnFiltered() {
        CitiesSearchQuery query = CitiesSearchQuery.builder()
                .namePattern("PA*").build();
        List<City> cities = tested.fetchCities(query);
        assertEquals(3, cities.size());
        long incorrectCities = cities.stream().filter(c -> !c.getName().startsWith("PA")).count();
        assertEquals(0, incorrectCities);
    }
}