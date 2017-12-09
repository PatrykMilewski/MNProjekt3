package com.projekt;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.*;

public class MainWindowController {
    private CsvReader csvReader = new CsvReader();
    private int iterator = 1;
    
    private String inputPath = "";
    
    @FXML
    private TextField textField;
    
    @FXML
    public void ButtonLagrange() {
        try {
            csvReader.read(inputPath + ".csv");
            Interpolation(iterator, new LagrangeInterpolator(csvReader.pointsArray));
            iterator++;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    public void ButtonSpline() {
        try {
            csvReader.read(inputPath + ".csv");
            Interpolation(iterator, new SplineInterpolator(csvReader.pointsArray));
            iterator++;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    void TextEntered() {
        inputPath = textField.getText();
    }
    
    private void Interpolation(int fileNumber, Interpolate interpolator) throws IOException {
        final int pointsAmount = 1000;
        
        double chartDelta = SinglePoint.max - SinglePoint.min;
        double delta = chartDelta / pointsAmount;
        double start = SinglePoint.min;
    
        try (Writer output = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(inputPath + fileNumber + ".out"), "utf-8"))) {
            for (int i = 0; i <= pointsAmount; i++) {
                output.write(start + "\t" + interpolator.interpolation(start) + "\n");
                start += delta;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
