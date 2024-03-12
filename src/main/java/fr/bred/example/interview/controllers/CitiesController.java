package fr.bred.example.interview.controllers;

import java.util.List;

import fr.bred.example.interview.rest.apis.ApiApi;
import fr.bred.example.interview.rest.dtos.City;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import fr.bred.example.interview.services.CitiesService;

@RestController
public class CitiesController implements ApiApi {
	private final CitiesService citiesService;

	public CitiesController(CitiesService citiesService) {
		this.citiesService = citiesService;
	}

	@Override
	public ResponseEntity<List<City>> getCities(String namePattern, String zipCodePattern, Integer limit, Integer start,
			String sort, String order) {
		List<City> cities = citiesService.getCities(namePattern, zipCodePattern, limit, start, sort, order);
		return ResponseEntity.ok(cities);
	}

	@Override
	public ResponseEntity<City> nearestCity(String x, String y) {
		try {
			City nearestCity = citiesService.findNearestCity(x, y);
			return ResponseEntity.ok(nearestCity);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
}
