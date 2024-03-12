package fr.bred.example.interview.services;

import fr.bred.example.interview.rest.dtos.City;

import java.util.List;

public interface CitiesService {

	List<City> getCities(String namePattern, String zipCodePattern, Integer limit, Integer start, String sort,
			String order);

	City findNearestCity(String x, String y) throws IllegalArgumentException;

}
