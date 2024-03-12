package fr.bred.example.interview.utils;

public abstract class HelperFunctions {

    private HelperFunctions() {
        //Hide constructor
    }

    public static Double errorSafeDoubleToString(String value) {
        if(value == null){
            return null;
        }
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static double calculateDistanceFromCoordinates(double x1, double y1, double x2, double y2) {
        double xDiff = x1 - x2;
        double yDiff = y1 - y2;
        return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }
}
