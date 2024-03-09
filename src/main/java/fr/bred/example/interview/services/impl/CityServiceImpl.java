package fr.bred.example.interview.services.impl;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.openapitools.model.City;
import org.springframework.stereotype.Service;

import fr.bred.example.interview.services.CityService;
import fr.bred.example.interview.utile.CityUtils;

@Service
public class CityServiceImpl implements CityService {

	private final List<City> cities;

	public CityServiceImpl() {
		this.cities = CityUtils.loadCitiesFromJson();
	}

	@Override
	public List<City> getCities(String namePattern, String zipCodePattern, Integer limit, Integer start, String sort,
			String order) {
		List<City> filteredCities = CityUtils.filterCities(cities, namePattern, zipCodePattern, sort, order);

		// Gérer la pagination si nécessaire
		if (start != null && start >= 0 && limit != null && limit > 0) {
			filteredCities = filteredCities.stream().skip(start).limit(limit).collect(Collectors.toList());
		}

		return filteredCities;
	}

	@Override
	public City findNearestCity(String x, String y) {
		double xCoord = Double.parseDouble(x);
		double yCoord = Double.parseDouble(y);

		return cities
				.stream().min(Comparator.comparingDouble(city -> CityUtils
						.calculateDistance(city.getCoordinates().getX(), city.getCoordinates().getY(), xCoord, yCoord)))
				.orElse(null);
	}

}
