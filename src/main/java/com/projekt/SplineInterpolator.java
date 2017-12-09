package com.projekt;

class SplineInterpolator implements Interpolate {
    private double[] data;
    private SinglePoint pointsArray[];
    
    SplineInterpolator(SinglePoint pointsArray[]) {
        this.pointsArray = pointsArray;
        SplineSetup();
    }
    
    private void SplineSetup() {
        final int size = pointsArray.length;
        double slopes[] = new double[size - 1];
        double tab[] = new double[size];
        
        for (int i = 0; i < size - 1; i++) {
            double dx = pointsArray[i+1].distance - pointsArray[i].distance;
            
            slopes[i] = (pointsArray[i+1].elevation -  pointsArray[i].elevation) / dx;
        }
        
        tab[0] = slopes[0];
        for (int i = 1; i < size - 1; i++) {
            tab[i] = (slopes[i - 1] + slopes[i]) / 2d;
        }
        tab[size - 1] = slopes[size - 2];
        
        for (int i = 0; i < size - 1; i++) {
            if (slopes[i] == 0f) {
                tab[i] = 0f;
                tab[i + 1] = 0f;
            } else {
                double alpha = tab[i] / slopes[i];
                double beta = tab[i + 1] / slopes[i];
                double h = Math.hypot(alpha, beta);
                if (h > 3f) {
                    double t = 3f / h;
                    tab[i] = t * alpha * slopes[i];
                    tab[i + 1] = t * beta * slopes[i];
                }
            }
        }
        this.data = tab;
    }
    
    public double interpolation(double input) {
        // Handle the boundary cases.
        final int size = pointsArray.length;
        if (Double.isNaN(input))
            return input;
        
        if (input <= pointsArray[0].distance)
            return pointsArray[0].elevation;
        
        if (input >= pointsArray[size - 1].distance)
            return pointsArray[size - 1].elevation;
        
        
        int i = 0;
        // check if we already got this value, and iterate to part of array
        // where we want to do calculations
        while (input >= pointsArray[i + 1].distance) {
            i += 1;
            if (input == pointsArray[i].distance)
                return pointsArray[i].elevation;
        }
        
        // function argument is between pointsArray[i and i + 1].distance
        double topX = pointsArray[i + 1].distance;      // bottom of the interval
        double bottomX = pointsArray[i].distance;       // top of the interval
        double dx = topX - bottomX;
        double t = (input - bottomX) / dx;
        
        
        // function value is between pointsArray[i and i + 1].elevation
        double topY = pointsArray[i + 1].elevation;
        double bottomY = pointsArray[i].elevation;
        double bottomM = data[i];
        double topM = data[i + 1];
        
        //h_ii are the basis functions for the cubic Hermite spline in factorized version
        double h00 = (1 + 2 * t) * Math.pow(1 - t, 2);  // (1 + 2t) (1 - t)^2
        double h10 = t * Math.pow(1 - t, 2);            // t (1 - t)^2
        double h01 = (3 - 2 * t) * Math.pow(t, 2);      // t^2 (3 - 2t)
        double h11 = (t - 1) * Math.pow(t, 2);          // t^2 (t - 1)
        
        return bottomY * h00 + dx * bottomM * h10 + topY * h01 + dx * topM * h11;
    }
}
