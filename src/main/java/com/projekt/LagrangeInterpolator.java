package com.projekt;

class LagrangeInterpolator implements Interpolate {
    private SinglePoint pointsArray[];
    
    LagrangeInterpolator(SinglePoint pointsArray[]) {
        this.pointsArray = pointsArray;
    }
    
    public double interpolation(double input){
        double t, result = 0.0;
        int pointsAmount = pointsArray.length;
        
        for (int i = 0; i < pointsAmount; i++) {
            t = 1.0;
            
            for (int j = 0; j < pointsAmount; j++) {
                if (t == 0.0)
                    break;
                
                if (i != j)
                    t *= ((input - pointsArray[j].distance)/(pointsArray[i].distance - pointsArray[j].distance));
            }
            result += t * pointsArray[i].elevation;
        }
        return result;
    }
}
