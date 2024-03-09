package fr.bred.example.interview.services;

import java.util.List;

import org.openapitools.model.City;

public interface CityService {

	List<City> getCities(String namePattern, String zipCodePattern, Integer limit, Integer start, String sort,
			String order);

	City findNearestCity(String x, String y);

}
