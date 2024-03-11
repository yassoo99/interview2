package fr.bred.example.interview.services;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.openapitools.model.City;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.bred.example.interview.services.impl.CityServiceImpl;
import fr.bred.example.interview.utils.CityUtils;

public class CityServiceTest {

	private CityService tested;
	private List<City> cities;
	private CityUtils cityUtils;

	@BeforeEach
	void setup() {
		cityUtils = mock(CityUtils.class);
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			cities = objectMapper.readValue(new File("src/test/resources/cities2.json"),
					new TypeReference<List<City>>() {
					});
		} catch (IOException e) {
			cities = List.of();
		}
		tested = new CityServiceImpl(cities, cityUtils);

	}

	@Test
	public void testGetCities_WithLimitAndSort() {
		when(cityUtils.filterCities(cities, "PAR*", null, "name", "asc")).thenReturn(cities);

		List<City> result = tested.getCities("PAR*", null, 10, 0, "name", "asc");

		assertEquals(3, result.size());
		assertEquals("PASILLY", result.get(0).getName());
		assertEquals("PARNES", result.get(1).getName());
		assertEquals("PARIS", result.get(2).getName());

	}

	@Test
	public void testGetCities_WithoutLimitAndSort() {
		when(cityUtils.filterCities(cities, "P", null, null, null)).thenReturn(cities);

		List<City> result = tested.getCities("P", null, null, null, null, null);

		assertEquals(3, result.size());
		assertEquals("PASILLY", result.get(0).getName());
		assertEquals("PARNES", result.get(1).getName());
		assertEquals("PARIS", result.get(2).getName());
	}

}
