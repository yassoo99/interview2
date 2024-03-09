package fr.bred.example.interview.controllers;

import java.util.List;

import org.openapitools.api.ApiApi;
import org.openapitools.model.City;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import fr.bred.example.interview.services.CityService;

@RestController
public class CityController implements ApiApi {
	private final CityService cityService;

	public CityController(CityService cityService) {
		this.cityService = cityService;
	}

	@Override
	public ResponseEntity<List<City>> getCities(String namePattern, String zipCodePattern, Integer limit, Integer start,
			String sort, String order) {
		List<City> cities = cityService.getCities(namePattern, zipCodePattern, limit, start, sort, order);
		return ResponseEntity.ok(cities);
	}

	@Override
	public ResponseEntity<City> nearestCity(String x, String y) {
		try {
			City nearestCity = cityService.findNearestCity(x, y);
			return ResponseEntity.ok(nearestCity);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}
}
