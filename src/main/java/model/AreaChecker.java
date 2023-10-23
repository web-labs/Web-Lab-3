package model;

public class AreaChecker {
    public static boolean isInArea(double x, double y, double r){
        if (x >= 0 && y >= 0){
            return x < r && y <= r;
        }

        if (x <= 0 && y >= 0){
            return ((Math.pow(x, 2)) + Math.pow(y, 2) <= Math.pow(r/2, 2));
        }

        if (x >= 0 && y <= 0){
            return (x <= r && y >= -r/2 && y >= (x/2 - r/2));
        }

        return false;
    }
}
