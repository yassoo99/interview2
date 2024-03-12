package fr.bred.example.interview.services.impl;

import java.util.Comparator;
import java.util.List;

import fr.bred.example.interview.data.CitiesDatasource;
import fr.bred.example.interview.data.CitiesSearchQuery;
import fr.bred.example.interview.functions.CityDistanceCalculator;
import fr.bred.example.interview.rest.dtos.City;
import fr.bred.example.interview.rest.dtos.Point;
import org.springframework.stereotype.Service;

import fr.bred.example.interview.services.CitiesService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CitiesServiceImpl implements CitiesService {

	private CityDistanceCalculator cityDistanceCalculator;
	private CitiesDatasource citiesDatasource;

	
	@Override
	public List<City> getCities(String namePattern, String zipCodePattern, Integer limit, Integer start, String sort,
			String order) {
		CitiesSearchQuery query = CitiesSearchQuery.builder()
				.namePattern(namePattern)
				.zipCodePattern(zipCodePattern)
				.limit(limit)
				.start(start)
				.sort(sort)
				.order(order).build();
		return citiesDatasource.fetchCities(query);
	}

	@Override
	public City findNearestCity(String x, String y) throws IllegalArgumentException {
		Point initPos = new Point();
		initPos.setX(x);
		initPos.setY(y);

		return citiesDatasource.fetchCities()
				.stream().filter(city -> city.getCoordinates() != null)
				.min(Comparator.comparingDouble(city ->
						cityDistanceCalculator.calculateDistanceToCity(initPos, city.getCoordinates())))
				.orElse(null);
	}
}
