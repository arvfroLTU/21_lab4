package Sim;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WriteDoublesToCSV {

    // Method to write a list of doubles into a CSV file
    public void writeDoublesToCSV(List<Double> values, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            // Write the header (optional)
            writer.write("DoubleValues");
            writer.newLine();
            
            // Loop through each double and write it to the file
            for (Double value : values) {
                writer.write(Double.toString(value));
                writer.newLine();  // Move to the next line
            }

            System.out.println("Doubles written to CSV file successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Example ArrayList of doubles
        List<Double> values = new ArrayList<>();
        values.add(12.34);
        values.add(56.78);
        values.add(90.12);
        values.add(45.67);
        values.add(99.99); // Example of appending a value

        // Specify the CSV file name where the doubles will be documented
        String fileName = "doubles.csv";

        // Create an instance of the class to call the method
        WriteDoublesToCSV writer = new WriteDoublesToCSV();
        
        // Call the method to write the doubles to the CSV file
        writer.writeDoublesToCSV(values, fileName);
    }
}