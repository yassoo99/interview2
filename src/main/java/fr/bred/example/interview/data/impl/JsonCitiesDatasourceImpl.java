package fr.bred.example.interview.data.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.bred.example.interview.data.CitiesDatasource;
import fr.bred.example.interview.data.CitiesSearchQuery;
import fr.bred.example.interview.rest.dtos.City;
import fr.bred.example.interview.services.impl.CitiesServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class JsonCitiesDatasourceImpl implements CitiesDatasource {

    private List<City> allCities;
    private boolean dataInit = false;

    @Override
    public List<City> fetchCities() {
        initData();

        return allCities;
    }

    @Override
    public List<City> fetchCities(CitiesSearchQuery query) {
        initData();

        Stream<City> citiesStream = allCities.stream();

        //Filter
        boolean shouldFilterByName = StringUtils.isNoneBlank(query.getNamePattern());
        boolean shouldFilterByZipCode = StringUtils.isNoneBlank(query.getZipCodePattern());

        citiesStream = citiesStream.filter(city -> {
            if (shouldFilterByName && !city.getName().matches(query.getNamePattern().replace("*", ".*")))
                return false;
            return !shouldFilterByZipCode || city.getZipCode().matches(query.getZipCodePattern().replace("*", ".*"));
        });

        // Sort
        String sort = query.getSort();
        boolean desc = StringUtils.equals(query.getOrder(), "desc");
        if (StringUtils.isNoneBlank(sort)) {
            if (sort.equals("name")) {
                citiesStream = citiesStream.sorted((c1, c2) -> compare(c1.getName(), c2.getName(), desc));
            } else if (sort.equals("zipCode")) {
                citiesStream = citiesStream.sorted((c1, c2) -> compare(c1.getZipCode(), c2.getZipCode(), desc) );
            }
        }

        // Pagination
        boolean shouldPaginate = query.getStart() != null && query.getStart() >= 0 && query.getLimit() != null && query.getLimit() > 0;
        if (shouldPaginate) {
            citiesStream = citiesStream.skip(query.getStart()).limit(query.getLimit());
        }

        return citiesStream.toList();
    }

    private int compare(String val1, String val2, boolean desc) {
        return val1.compareToIgnoreCase(val2) * (desc ? -1 : 1);
    }

    private void initData() {
        if(!dataInit) {
            allCities = loadCitiesFromJson();
            dataInit = true;
        }
    }

    public List<City> loadCitiesFromJson() {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<City>> typeReference = new TypeReference<>() {
        };
        try (InputStream inputStream = CitiesServiceImpl.class.getResourceAsStream("/cities.json")) {
            return mapper.readValue(inputStream, typeReference);
        } catch (IOException e) {
            e.printStackTrace();
            return List.of();
        }
    }
}
