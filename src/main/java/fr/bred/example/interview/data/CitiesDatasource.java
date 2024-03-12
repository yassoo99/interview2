package fr.bred.example.interview.data;

import java.util.List;

import fr.bred.example.interview.rest.dtos.City;

public interface CitiesDatasource {

    List<City> fetchCities();

    List<City> fetchCities(CitiesSearchQuery query);
}
