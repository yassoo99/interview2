package fr.bred.example.interview.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openapitools.model.City;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.*;

public class UtilsTest {

	private List<City> cities;
	private CityUtils tested;

	@BeforeEach
	void setUp() {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			cities = objectMapper.readValue(new File("src/test/resources/cities.json"),
					new TypeReference<List<City>>() {
					});
		} catch (IOException e) {
			cities = List.of();
		}
		tested = new CityUtils();
	}

	@Test
	void testFilterCities_AllCities() {
		String namePattern = ".*";
		String zipCodePattern = ".*";
		String sort = "name";
		String order = "asc";

		List<City> filteredCities = tested.filterCities(cities, namePattern, zipCodePattern, sort, order);

		assertEquals(5, filteredCities.size());
	}

	@Test
	void testFilterCities_FilterByName() {
		String namePattern = "PAR.*";
		String zipCodePattern = ".*";
		String sort = "name";
		String order = "asc";

		List<City> filteredCities = tested.filterCities(cities, namePattern, zipCodePattern, sort, order);

		assertEquals(3, filteredCities.size());
	}

	@Test
	void testFilterCities_FilterByZipCode() {
		String namePattern = ".*";
		String zipCodePattern = "75.*";
		String sort = "name";
		String order = "asc";

		List<City> filteredCities = tested.filterCities(cities, namePattern, zipCodePattern, sort, order);

		assertEquals(3, filteredCities.size());
	}

	@Test
	void testFilterCities_SortByNameDescending() {
		String namePattern = ".*";
		String zipCodePattern = ".*";
		String sort = "name";
		String order = "desc";

		List<City> filteredCities = tested.filterCities(cities, namePattern, zipCodePattern, sort, order);

		assertEquals(5, filteredCities.size());
		assertEquals("PARIS 11", filteredCities.get(2).getName());
		assertEquals("MARSEILLE", filteredCities.get(1).getName());
		assertEquals("BORDEAUX", filteredCities.get(0).getName());
	}
	
	@Test
    public void testCalculateDistance() {
        double x2 = 2.3522;
        double y2 = 48.8566;

        double distance = tested.calculateDistance("2.3522", "48.8566", x2, y2);

        assertEquals(0, distance, 0.001);
    }

}
