package com.projekt;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.stream.Stream;

class CsvReader {
    private String filePath;
    private File file;
    
    public SinglePoint pointsArray[];
    
    CsvReader() {}
    
    private void readCsv() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            pointsArray = new SinglePoint[SinglePoint.counter];
            String line;
            int iterator = 0;
            while ((line = br.readLine()) != null) {
                if (!line.contains("Dystans (m),Wysokość (m)"))
                    pointsArray[iterator] = new SinglePoint(line);
                else
                    continue;
                iterator++;
            }
        }
    }
    
    private void checkLength() throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            stream.filter(line -> !line.contains("Dystans (m),Wysokość (m)"))
                    .forEach(line -> SinglePoint.counter++);
        }
    }
    
    void read(String filePath) throws IOException {
        this.filePath = filePath;
        file = new File(filePath);
        SinglePoint.Reset();
        checkLength();
        readCsv();
    }
}
