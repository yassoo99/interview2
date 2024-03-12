package fr.bred.example.interview.functions.impl;

import fr.bred.example.interview.functions.CityDistanceCalculator;
import fr.bred.example.interview.rest.dtos.City;
import fr.bred.example.interview.rest.dtos.Point;
import fr.bred.example.interview.utils.HelperFunctions;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
public class CityDistanceCalculatorImpl implements CityDistanceCalculator {


    @Override
    public double calculateDistanceToCity(Point initPos, Point cityPos) throws IllegalArgumentException {
        Double cityX = HelperFunctions.errorSafeDoubleToString(cityPos.getX());
        Double cityY = HelperFunctions.errorSafeDoubleToString(cityPos.getY());

        Double initX = HelperFunctions.errorSafeDoubleToString(initPos.getX());
        Double initY = HelperFunctions.errorSafeDoubleToString(initPos.getY());

        if(cityX == null || cityY == null || initX == null || initY == null){
            throw new IllegalArgumentException("Invalid coordinates");
        }

        return HelperFunctions.calculateDistanceFromCoordinates(initX, initY, cityX, cityY);
    }
}
