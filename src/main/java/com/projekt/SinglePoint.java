package com.projekt;

class SinglePoint {
    private static String splitString[] = new String[2];
    private static boolean maxFlag = false, minFlag = false;
    static int counter = 0;
    static double max, min;
    
    double distance, elevation;
    
    SinglePoint(String distanceAndElevation) {
        splitString = distanceAndElevation.split(",");
        distance = Double.parseDouble(splitString[0]);
        elevation = Double.parseDouble(splitString[1]);
        
        
        // find extremes of input values
        if (maxFlag && max < distance)
            max = distance;
        else if (!maxFlag) {
            max = distance;
            maxFlag = true;
        }
        if (minFlag && min > distance)
            min = distance;
        else if (!minFlag) {
            min = distance;
            minFlag = true;
        }
    }
    
    static void Reset() {
        counter = 0;
        maxFlag = false;
        minFlag = false;
    }
}
