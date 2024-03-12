package fr.bred.example.interview.functions;

import fr.bred.example.interview.rest.dtos.Point;
public interface CityDistanceCalculator {

    double calculateDistanceToCity(Point initPos, Point cityPos);
}
