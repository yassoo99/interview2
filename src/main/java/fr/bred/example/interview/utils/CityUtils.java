package fr.bred.example.interview.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import org.openapitools.model.City;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.bred.example.interview.services.impl.CityServiceImpl;

public class CityUtils {

	public double calculateDistance(String x1, String y1, double x2, double y2) {
		double xDiff = Double.parseDouble(x1) - x2;
		double yDiff = Double.parseDouble(y1) - y2;
		return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
	}

	public List<City> filterCities(List<City> cities, String namePattern, String zipCodePattern, String sort,
			String order) {
		return cities.stream()
				.filter(city -> (namePattern == null || city.getName().matches(namePattern.replace("*", ".*")))
						&& (zipCodePattern == null || city.getZipCode().matches(zipCodePattern.replace("*", ".*"))))
				.sorted((c1, c2) -> {
					if (sort == null)
						return 0;
					if (sort.equals("name")) {
						return c1.getName().compareToIgnoreCase(c2.getName());
					} else if (sort.equals("zipCode")) {
						return c1.getZipCode().compareToIgnoreCase(c2.getZipCode());
					}
					return 0;
				}).collect(Collectors.toList());
	}

	public List<City> loadCitiesFromJson() {
		ObjectMapper mapper = new ObjectMapper();
		TypeReference<List<City>> typeReference = new TypeReference<>() {
		};
		try (InputStream inputStream = CityServiceImpl.class.getResourceAsStream("/cities.json")) {
			return mapper.readValue(inputStream, typeReference);
		} catch (IOException e) {
			e.printStackTrace();
			return List.of();
		}
	}

}
