package fr.bred.example.interview.services.impl;

import fr.bred.example.interview.data.CitiesDatasource;
import fr.bred.example.interview.data.CitiesSearchQuery;
import fr.bred.example.interview.functions.CityDistanceCalculator;
import fr.bred.example.interview.rest.dtos.City;
import fr.bred.example.interview.rest.dtos.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;

class CitiesServiceImplTest {


    private CityDistanceCalculator cityDistanceCalculator;
    private CitiesDatasource citiesDatasource;
    private CitiesServiceImpl tested;

    @BeforeEach
    void setUp() {
        cityDistanceCalculator = Mockito.mock(CityDistanceCalculator.class);
        citiesDatasource = Mockito.mock(CitiesDatasource.class);
        tested = new CitiesServiceImpl(cityDistanceCalculator, citiesDatasource);
    }

    @Test
    void givenGoodParams_whenFetchCities_thenSuccess() {

        String namePattern = "P";
        String zipCodePattern = "P";
        Integer limit = 10;
        Integer start = 0;
        String sort = "name";
        String order = "asc";

        Mockito.when(citiesDatasource.fetchCities(Mockito.any(CitiesSearchQuery.class))).thenReturn(List.of(new City()));

        // Test
        List<City> cities = tested.getCities(namePattern, zipCodePattern, limit, start, sort, order);


        ArgumentCaptor<CitiesSearchQuery> queryCaptor = ArgumentCaptor.forClass(CitiesSearchQuery.class);
        Mockito.verify(citiesDatasource).fetchCities(queryCaptor.capture());
        CitiesSearchQuery query = queryCaptor.getValue();

        assertEquals(namePattern, query.getNamePattern());
        assertEquals(zipCodePattern, query.getZipCodePattern());
        assertEquals(limit, query.getLimit());
        assertEquals(start, query.getStart());
        assertEquals(sort, query.getSort());
        assertEquals(order, query.getOrder());

        assertEquals(1, cities.size());
    }

    @Test
    void givenGoodParams_whenFindNearestCity_thenSuccess() {
        List<City> cities = List.of(
                createCity("Paris", "75000", "1", "1"),
                createCity("Nantes", "44000", "10", "10")
        );
        Mockito.when(citiesDatasource.fetchCities()).thenReturn(cities);


        Point initPos = new Point();
        initPos.setX("8");
        initPos.setX("8");

        Mockito.when(cityDistanceCalculator.calculateDistanceToCity(Mockito.any(Point.class), eq(cities.get(0).getCoordinates()))).thenReturn(9.0);
        Mockito.when(cityDistanceCalculator.calculateDistanceToCity(Mockito.any(Point.class), eq(cities.get(1).getCoordinates()))).thenReturn(4.0);

        City nearestCity = tested.findNearestCity(initPos.getX(), initPos.getY());

        assertNotNull(nearestCity);
        assertEquals(cities.get(1).getName(), nearestCity.getName());
    }


    private City createCity(String name, String zipCode, String x, String y) {
        City city = new City();
        city.setName(name);
        city.setZipCode(zipCode);
        Point point = new Point();
        point.setX(x);
        point.setY(y);
        city.setCoordinates(point);
        return city;
    }
}